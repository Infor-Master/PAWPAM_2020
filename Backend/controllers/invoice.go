package controllers

import (
	"net/http"
	"projetoapi/model"
	"projetoapi/services"
	"strconv"
	"github.com/gin-gonic/gin"

	"flag"
	"log"
)


/**
 * Procura na lista de faturas do utilizador se existe uma fatura com o id enviado por parâmetro 
**/
func GetInvoice(c *gin.Context) {

	var invoice model.Invoice

	// Vai buscar o ID e verifica se é nulo ou não
	id, err := strconv.ParseUint(c.Param("id"), 10, 32)

	if err != nil{
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Invalid Invoice ID!"})
		return nil
	}

	uintID := uint(id)

	// Vai buscar a lista de invoices e verifica se é nula ou não
	invoices, err := GetUserinvoice

	if err != nil{
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Lista de faturas vazia!"})
		return nil
	}

	// Procura se o ID da fatura existe na lista de faturas do utiliador
	for _, invoice := range invoices {
		
		if invoice.ID == uintID {
			return &invoice
		}
	}

	c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Não existe na sua lista de faturas!"})
	return nil

}

/**
* Adiciona à tabela das faturas uma nova fatura com todos os parâmetros da mesma
**/
func AddInvoice(c *gin.Context) {
	
	var addr = flag.String("addr", ":8080", "http server address")

	flag.Parse()

	http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
		ServeWs(w, r)
	})

	log.Fatal(http.ListenAndServe(*addr, nil))

	var invoice model.Invoice

	if err := c.ShouldBindJSON(&invoice); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Check syntax!"})
		return
	}
	services.Db.Save(&invoice)
	c.JSON(http.StatusCreated, gin.H{"status": http.StatusCreated, "message": "Create successful!", "resourceId": invoice.ID})
}

/**
 * Procura na lista de faturas se existe uma fatura com o id enviado por parâmetro e elinina-a da tabela de faturas
**/
func DeleteInvoice(c *gin.Context) {
	var invoice model.Invoice

	id := c.Param("id")

	services.Db.Where("id = ?", id).Find(&invoice, id)

	if invoice.ID == 0 {
		c.JSON(http.StatusNotFound, gin.H{"status": http.StatusNotFound, "message": "None found!"})
		return
	}

	services.Db.Delete(&invoice)

	c.JSON(http.StatusOK, gin.H{"status": http.StatusOK, "message": "Delete succeeded!"})
}