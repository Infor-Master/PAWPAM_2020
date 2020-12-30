package main

import (
	"projetoapi/model"
	"projetoapi/routes"
	"projetoapi/services"

	"github.com/gin-gonic/gin"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	swaggerFiles "github.com/swaggo/files"
	ginSwagger "github.com/swaggo/gin-swagger"
)

var identityKey = "id"

func init() {
	services.OpenDatabase()
	services.Db.AutoMigrate(&model.User{})
	services.Db.AutoMigrate(&model.Invoice{})

	var user model.User
	user.Username = "User"
	user.Name = "Test User Account"
	user.Password = services.HashAndSalt([]byte("user123"))
	services.Db.Save(&user)

	defer services.Db.Close()
}

func main() {

	services.FormatSwagger()

	// Creates a gin router with default middleware:
	// logger and recovery (crash-free) middleware
	router := gin.New()
	router.Use(gin.Logger())
	router.Use(gin.Recovery())

	router.NoRoute(func(c *gin.Context) {
		c.JSON(404, gin.H{"code": "PAGE_NOT_FOUND", "message": "Page not found"})
	})

	router.GET("/api/swagger/*any", ginSwagger.WrapHandler(swaggerFiles.Handler))

	invoice := router.Group("/api/invoices")
	invoice.Use(services.AuthorizationRequired())
	{
		invoice.GET("/user/:id", routes.GetUserInvoices)
	}

	user := router.Group("/api/user")
	user.Use(services.AuthorizationRequired())
	{
		user.POST("/invoices", routes.AddInvoice)
		user.DELETE("/invoices/:id", routes.DeleteInvoice)
		//user.DELETE("/users/:id", routes.DeleteUser)
		user.POST("/users", routes.Register)
	}

	auth := router.Group("/api")
	{
		auth.POST("/login", routes.GenerateToken)
		auth.POST("/signup", routes.Register)
		auth.PUT("/refresh_token", services.AuthorizationRequired(), routes.RefreshToken)
	}

	router.Run(":8081")
}
