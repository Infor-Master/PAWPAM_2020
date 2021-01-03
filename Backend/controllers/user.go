package controllers

import (
	"net/http"
	"projetoapi/model"
	"projetoapi/services"
	"io/ioutil"
	"strings"
	"strconv"
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

func UpdateInfo(c *gin.Context) {
	
	var auxUser model.User
	var values[]string

	data, _ := ioutil.ReadAll(c.Request.Body)
	s := string(data)
	aux := strings.Split(s, ",")


	for i := 0; i <6; i++ {
		temp := strings.Split(aux[i], ":")
		values =append(values,temp[1])
	}

	id:= values[0]
	name:= strings.ReplaceAll(values[1],"\"","")
	username:= strings.ReplaceAll(values[5],"\"","")
	nif:=values[2]
	password:= strings.ReplaceAll(values[3],"\"","")
	newPassword:= strings.ReplaceAll(values[4],"\"","")

	services.Db.Find(&auxUser, "id = ?", id)
	if auxUser.Username == "" {
		c.JSON(http.StatusUnauthorized, gin.H{"status": http.StatusUnauthorized, "message": "Invalid User!"})
		return
	}

	if(!services.ComparePasswords(auxUser.Password,[]byte(password))){
		c.JSON(http.StatusUnauthorized, gin.H{"status": http.StatusNotFound, "message": "Invalid Password!"})
		return
	}

	if(newPassword!=""){
		password = services.HashAndSalt([]byte(newPassword))
	}else{
		password = services.HashAndSalt([]byte(password))
	}


	services.Db.Model(&auxUser).Update("name",name)

	//services.Db.Updates(&auxUser)

	/*sqlUpdate := `UPDATE users SET name = $1, username = $2, password=$3, nif = $4 WHERE id = $3;`
	err := services.Db.Exec(sqlUpdate, auxUser.Name, auxUser.Username,auxUser.Password,auxUser.NIF,id)
	if err != nil {
		panic(err)
	}*/

	c.JSON(http.StatusOK, gin.H{"status": http.StatusOK, "data": "result"})
	return
}