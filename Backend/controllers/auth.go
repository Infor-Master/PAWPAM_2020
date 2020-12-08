package controllers

import (
	"net/http"
	"projetoapi/model"
	"projetoapi/services"
	"github.com/gin-gonic/gin"
)

/**
 * Verifica se o utilizador existe:
 * - Caso exista gera um token e retorna-o
 * - Caso não exista envia um 401
**/
func LoginHandler(c *gin.Context) {
	var creds model.User
	var usr model.User

	if err := c.ShouldBindJSON(&creds); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Bad request!"})
		return
	}
	services.OpenDatabase()

	services.Db.Find(&usr, "username = ?", creds.Username)
	if usr.Username == "" {
		c.JSON(http.StatusUnauthorized, gin.H{"status": http.StatusUnauthorized, "message": "Invalid User!"})
		return
	}

	if(!services.ComparePasswords(usr.Password,[]byte(creds.Password))){
		c.JSON(http.StatusUnauthorized, gin.H{"status": http.StatusUnauthorized, "message": "Invalid Password!"})
		return
	}

	token := services.GenerateTokenJWT(usr)

		if token == "" {
			c.JSON(http.StatusUnauthorized, gin.H{"status": http.StatusUnauthorized, "message": "Access denied!"})
			return
		}
		defer services.Db.Close()
		c.JSON(http.StatusOK, gin.H{"status": http.StatusOK, "message": "Success!", "token": token})

}

/**
 * Dá refresh ao Token responsável pela autenticação
**/
func RefreshHandler(c *gin.Context) {

	user := model.User{
		Username: c.GetHeader("username"),
	}

	token := services.GenerateTokenJWT(user)

	if token == "" {
		c.JSON(http.StatusUnauthorized, gin.H{"status": http.StatusUnauthorized, "message": "Acesso não autorizado"})
		return
	}

	defer services.Db.Close()
	c.JSON(http.StatusOK, gin.H{"status": http.StatusNoContent, "message": "Token atualizado com sucesso!", "token": token})
}

/**
 * Regista um utilizador na base de dados
**/
func Register(c *gin.Context) {
	var user model.User

	if err := c.ShouldBindJSON(&user); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Check syntax!"})
		return
	}

	hash := services.HashAndSalt([]byte(user.Password))

	user.Password = hash

	services.Db.Save(&user)
	c.JSON(http.StatusCreated, gin.H{"status": http.StatusCreated, "message": "Create successful!", "resourceId": user.Name})
}
