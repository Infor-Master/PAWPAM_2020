package services

import (
	"fmt"
	"log"

	"github.com/streadway/amqp"
)

var Channel *amqp.Channel
var Hello_queue amqp.Queue

func failOnError(err error, msg string) {
	if err != nil {
		log.Fatalf("%s: %s", msg, err)
	}
}

func HelloWorld(ch *amqp.Channel, q amqp.Queue) {
	body := "hello_invoice"
	ch.Publish(
		"",     // exchange
		q.Name, // routing key
		false,  // mandatory
		false,  // immediate
		amqp.Publishing{
			ContentType: "text/plain",
			Body:        []byte(body),
		})
	log.Printf(" [x] Sent %s", body)

	fmt.Printf("Hello World!")
}

func RabbitMQinit() {
	conn, err := amqp.Dial("amqp://rabbitmq:rabbitmq@rabbitmq:5672/")
	failOnError(err, "Failed to connect to RabbitMQ")

	ch, err := conn.Channel()
	failOnError(err, "Failed to open a channel")

	Channel = ch

	q, err := ch.QueueDeclare(
		"hello", // name
		false,   // durable
		false,   // delete when unused
		false,   // exclusive
		false,   // no-wait
		nil,     // arguments
	)
	failOnError(err, "Failed to declare a queue")

	Hello_queue = q

	//helloWorld(Channel, Hello_queue)

	fmt.Println("Started listening...")
}
