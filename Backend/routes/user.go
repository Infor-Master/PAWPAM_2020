package routes

import (
	"projetoapi/controllers"

	"github.com/gin-gonic/gin"
)

func GetUserInvoices(c *gin.Context) {
	controllers.GetUserInvoices(c)
}

func UpdateInfo(c *gin.Context) {
	controllers.UpdateInfo(c)
}