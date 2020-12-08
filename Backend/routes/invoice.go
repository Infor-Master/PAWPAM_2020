package routes

import (
	"projetoapi/controllers"

	"github.com/gin-gonic/gin"
)

/**
 * Cada rota vai buscar a respetiva função à package dos controladores
**/

func AddInvoice(c *gin.Context) {
	controllers.AddInvoice(c)
}

func DeleteInvoice(c *gin.Context) {
	controllers.DeleteInvoice(c)
}

func GetInvoice(c *gin.Context) {
	controllers.GetInvoice(c)
}
