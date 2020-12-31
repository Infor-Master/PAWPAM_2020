package model

import (
	"github.com/jinzhu/gorm"
)

type Invoice struct {
	gorm.Model `swaggerignore:"true"`
	Image      string //base64 string
	Name       string
	UserID     int
	Info       string
}
