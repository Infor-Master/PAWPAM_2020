package controllers

import (
	"net/http"
	"projetoapi/model"
	"projetoapi/services"

	"fmt"
	"strconv"

	"github.com/gin-gonic/gin"
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

func UpdateInfo(c *gin.Context) {

	type Profile struct {
		ID          int
		Name        string
		NIF         string
		Password    string
		NewPassword string
		Username    string
	}

	var auxUser model.User
	var profile Profile

	if err := c.ShouldBindJSON(&profile); err != nil {
		fmt.Println(err)
		c.JSON(http.StatusBadRequest, gin.H{"status": http.StatusBadRequest, "message": "Check syntax!"})
		return
	}

	fmt.Println("ID: ", profile.ID)
	fmt.Println("Name: ", profile.Name)
	fmt.Println("Username: ", profile.Username)
	fmt.Println("NIF: ", profile.NIF)
	fmt.Println("Password: ", profile.Password)
	fmt.Println("NewPassword: ", profile.NewPassword)

	services.Db.Find(&auxUser, "id = ?", profile.ID)
	if auxUser.Username == "" {
		c.JSON(http.StatusUnauthorized, gin.H{"status": http.StatusUnauthorized, "message": "Invalid User!"})
		return
	}

	if !services.ComparePasswords(auxUser.Password, []byte(profile.Password)) {
		c.JSON(http.StatusUnauthorized, gin.H{"status": http.StatusNotFound, "message": "Invalid Password!"})
		return
	}

	if profile.NewPassword != "" {
		profile.Password = services.HashAndSalt([]byte(profile.NewPassword))
		services.Db.Model(&auxUser).Update("password", profile.Password)
	}

	services.Db.Model(&auxUser).Update("name", profile.Name)
	NIF, _ := strconv.Atoi(profile.NIF)
	services.Db.Model(&auxUser).Update("nif", NIF)

	//services.Db.Updates(&auxUser)

	/*sqlUpdate := `UPDATE users SET name = $1, username = $2, password=$3, nif = $4 WHERE id = $3;`
	err := services.Db.Exec(sqlUpdate, auxUser.Name, auxUser.Username,auxUser.Password,auxUser.NIF,id)
	if err != nil {
		panic(err)
	}*/

	c.JSON(http.StatusOK, gin.H{"status": http.StatusOK, "data": "result"})
	return
}
