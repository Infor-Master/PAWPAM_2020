package services

import (
	"log"
	"time"

	"github.com/jinzhu/gorm"
)

var Db *gorm.DB

func OpenDatabase() {
	for i := 0; i < 5; i++ {
		var err error
		Db, err = gorm.Open("postgres", "postgres://"+"admin"+":"+"passw0rd"+"@"+"database"+":"+"5432"+"/"+"apidb"+"?sslmode=disable")
		if err == nil {
			log.Printf("Connected to database")
			return
		}
		log.Printf("attempting to connect to database... ")
		time.Sleep(5 * time.Second)
		log.Printf(err.Error())
	}
	panic("Failed to connect to database")
}
