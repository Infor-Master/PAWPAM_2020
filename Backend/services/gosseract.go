package services

import (
	"encoding/base64"
	"strings"

	"github.com/otiai10/gosseract"
)

func ProcessImage(base64string string) (string, error) {
	client := gosseract.NewClient()
	defer client.Close()

	//Languages []string
	//client.SetLanguage()

	s := strings.Split(base64string, ",")

	data, err := base64.StdEncoding.DecodeString(s[1])
	if err != nil {
		return string(""), err
	}

	client.SetImageFromBytes(data)
	text, err := client.Text()
	if err != nil {
		return string(""), err
	}

	//fmt.Println(text)

	return string(text), nil
}
