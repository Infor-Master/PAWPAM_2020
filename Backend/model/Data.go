package model

import (
	"time"

	"github.com/jinzhu/gorm"
)

type Data struct {
	gorm.Model    `swaggerignore:"true"`
	Invoice       Invoice    `gorm:"foreignKey:InvoiceRefer"`
	InvoiceNumber int        `gorm:"not null"`
	InvoicePrice  float32    `gorm:"not null"`
	BilingDate    *time.Time `gorm:"not null"`
}
