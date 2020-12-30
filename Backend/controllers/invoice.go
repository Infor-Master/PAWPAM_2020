package controllers

import (
	"net/http"
	"projetoapi/model"
	"projetoapi/services"
	"strconv"

	"github.com/gin-gonic/gin"
	//"flag"
	//"log"
)

/**
 * Procura na lista de faturas do utilizador se existe uma fatura com o id enviado por parâmetro
 * nos parâmetros tem que contar o ID do user e o ID da fatura
**/
func GetInvoice(c *gin.Context) {

	// Vai buscar o ID e verifica se é nulo ou não
	invoice_ID, err := strconv.ParseUint(c.Param("invoiceID"), 10, 32)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Invalid Invoice ID!"})
		return
	}

	uintID := uint(invoice_ID)

	// Vai buscar a lista de invoices e verifica se é nula ou não

	var listInvoices = GetUserInvoices(c)

	if listInvoices == nil {
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Lista de faturas vazia!"})
		return
	}

	// Procura se o ID da fatura existe na lista de faturas do utiliador
	for _, invoice := range listInvoices {

		if invoice.ID == uintID {
			c.JSON(http.StatusOK, gin.H{"status": http.StatusOK, "data": invoice})
			return
		}
	}

	c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Não existe na sua lista de faturas!"})
	return

}

/**
* Adiciona à tabela das faturas uma nova fatura com todos os parâmetros da mesma
**/
func AddInvoice(c *gin.Context) {

	/*var addr = flag.String("addr", ":8080", "http server address")

	flag.Parse()

	http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
		ServeWs(w, r)
	})

	log.Fatal(http.ListenAndServe(*addr, nil))
	*/
	var invoice model.Invoice

	if err := c.ShouldBindJSON(&invoice); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Check syntax!"})
		return
	}

	text, err := services.ProcessImage(invoice.Image)

	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Cannot process image!"})
		return
	}

	invoice.Info = text

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
