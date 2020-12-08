package routes

import (
	"projetoapi/controllers"

	"github.com/gin-gonic/gin"
)

func GenerateToken(c *gin.Context) {
	controllers.LoginHandler(c)
}

func RefreshToken(c *gin.Context) {
	controllers.RefreshHandler(c)
}

func Register(c *gin.Context) {
	controllers.Register(c)
}
