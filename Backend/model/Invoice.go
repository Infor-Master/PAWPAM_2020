package model

import (
	"github.com/jinzhu/gorm"
)

type Invoice struct {
	gorm.Model `swaggerignore:"true"`
	Image      string `gorm:"not null"` //base64 string
	Name       string
	UserID     int `gorm:"not null"`
	Info       string
}
