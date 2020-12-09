package controllers

import (
	"net/http"
	"projetoapi/model"
	"projetoapi/services"

	"github.com/gin-gonic/gin"
)

/**
 * Obter todas as faturas de um utilizador
 * O parâmetro tem de passar o id do user
**/
func GetUserInvoices(c *gin.Context) []model.Invoice {

	var user model.User
	var invoice []model.Invoice

	user_ID := c.Param("user_ID")

	services.Db.First(&user, "id = ?", user_ID)
	services.Db.Model(&user).Related(&invoice, "invoice")

	c.JSON(http.StatusOK, gin.H{"status": http.StatusOK, "data": invoice})
	return invoice
}