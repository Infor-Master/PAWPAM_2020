package services

import (
	"github.com/jinzhu/gorm"
)

var Db *gorm.DB

func OpenDatabase() {
	var err error
	Db, err = gorm.Open("postgres", "postgres://"+"admin"+":"+"passw0rd"+"@"+"database"+":"+"5432"+"/"+"apidb"+"?sslmode=disable")
	if err != nil {
		panic("failed to connect database")
	}
}
