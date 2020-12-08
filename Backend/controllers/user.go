package controllers

import (
	"net/http"
	"projetoapi/model"
	"projetoapi/services"

	"github.com/gin-gonic/gin"
)

/**
 * Obter todas as faturas de um utilizador
**/
func GetUserInvoices(c *gin.Context) {

	var user model.User
	var invoice []model.Invoice

	var claims = services.GetClaims(c)

	if claims == nil {
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Something went bad!"})
		return
	}

	services.Db.First(&user, "id = ?", claims.Id)
	services.Db.Model(&user).Related(&invoice, "invoice")

	c.JSON(http.StatusOK, gin.H{"status": http.StatusOK, "data": invoice})
}