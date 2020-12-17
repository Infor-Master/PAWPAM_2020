package model

import "github.com/jinzhu/gorm"

type User struct {
	gorm.Model `swaggerignore:"true"`
	Username   string `gorm:"unique;not null"`
	Password   string `gorm:"not null"`
	Name       string `gorm:"not null"`
	NIF        int    `gorm:"unique;not null"`
}
