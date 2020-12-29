package controllers

import (
	"net/http"
	"projetoapi/model"
	"projetoapi/services"

	"github.com/gin-gonic/gin"
	"fmt"
)

/**
 * Obter todas as faturas de um utilizador
 * O parâmetro tem de passar o id do user
**/
func GetUserInvoices(c *gin.Context) []model.Invoice {

	var user model.User
	var invoices []model.Invoice

	id := c.Param("id")
	fmt.Println(id)
	services.Db.First(&user, "id = ?", id)

	services.Db.Model(&user).Related(&invoices, "invoices")

	c.JSON(http.StatusOK, gin.H{"status": http.StatusOK, "data": invoices})
	return invoices
}