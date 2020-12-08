package model

import "github.com/jinzhu/gorm"
import "github.com/qor/media/filesystem"	// se necessário

type MyFileSystem struct {
	filesystem.FileSystem
}