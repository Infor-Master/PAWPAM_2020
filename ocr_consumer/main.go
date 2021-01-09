package main

import (
	"encoding/json"
	"log"
	"projetoapi/model"
	"projetoapi/services"
	"strconv"

	_ "github.com/jinzhu/gorm/dialects/postgres"
	"github.com/streadway/amqp"
)

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
	}
}

func OCRInvoice(msg []byte) {

	type Invoice struct {
		Image string `json:"Image"`
		ID    string `json:"ID"`
	}

	data := Invoice{}
	json.Unmarshal(msg, &data)

	text, err := services.ProcessImage(data.Image)
	if err != nil {
		failOnError(err, "cannot proccess image")
		return
	}

	var invoice model.Invoice
	id, err := strconv.Atoi(data.ID)
	services.Db.First(&invoice, "id = ?", id)

	log.Printf(`"Saving Invoice %s with user %d..."`, invoice.Name, invoice.UserID)

	services.Db.Model(&invoice).Update("info", text)
}

func init() {
	services.OpenDatabase()
}

func main() {
	conn, err := amqp.Dial("amqp://rabbitmq:rabbitmq@rabbitmq:5672/")
	failOnError(err, "Failed to connect to RabbitMQ")

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")

	q, err := ch.QueueDeclare(
		"invoices", // name
		false,      // durable
		false,      // delete when unused
		false,      // exclusive
		false,      // no-wait
		nil,        // arguments
	)
	failOnError(err, "Failed to declare a queue")

	msgs, err := ch.Consume(
		q.Name,             // queue
		"OCR Processor #1", // consumer
		true,               // auto-ack
		false,              // exclusive
		false,              // no-local
		false,              // no-wait
		nil,                // args
	)
	failOnError(err, "Failed to register a consumer")

	forever := make(chan bool)

	go func() {
		for d := range msgs {
			OCRInvoice(d.Body)
			//log.Printf("Received a message: %s", d.Body)
		}
	}()

	log.Printf(" [*] Waiting for messages. To exit press CTRL+C")
	<-forever
}
