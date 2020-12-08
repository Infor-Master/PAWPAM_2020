package model

import (
	"github.com/jinzhu/gorm"
)

type Invoice struct {
	gorm.Model `swaggerignore:"true"`
	Image      FileSystem
	User       User `gorm:"foreignKey:UserRefer"` // Usa a chave primária por defeito
	Notes      *string
}
