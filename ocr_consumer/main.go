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
		Image  string `json:"Image"`
		Name   string `json:"Name"`
		UserID string `json:"UserID"`
		Info   string
	}

	data := Invoice{}
	json.Unmarshal(msg, &data)

	text, err := services.ProcessImage(data.Image)
	if err != nil {
		failOnError(err, "cannot proccess image")
		return
	}
	log.Printf(text)

	var invoice model.Invoice
	invoice.Image = data.Image
	invoice.Name = data.Name
	userid, err := strconv.Atoi(data.UserID)
	invoice.UserID = userid
	invoice.Info = text

	log.Printf(`"Saving Invoice %s with user %d..."`, invoice.Name, invoice.UserID)

	services.Db.Save(&invoice)
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
