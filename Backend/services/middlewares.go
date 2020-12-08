package services

import (
	"net/http"
	"projetoapi/model"

	"github.com/dgrijalva/jwt-go"
	"github.com/gin-gonic/gin"
)

func AuthorizationRequired() gin.HandlerFunc {

	return func(c *gin.Context) {
		if !ValidateTokenJWT(c) {
			c.JSON(http.StatusUnauthorized, gin.H{"status": http.StatusUnauthorized, "message": "Not authorized"})
			c.Abort()
		} else {
			var tokenInput, _, _ = GetAuthorizationToken(c)
			token, _ := jwt.ParseWithClaims(tokenInput, &model.Claims{}, func(token *jwt.Token) (interface{}, error) {
				return JwtKey, nil
			})

			if claims, ok := token.Claims.(*model.Claims); ok && token.Valid {
				c.Set("username", claims.Username)
			}
			OpenDatabase()

			// before request
			c.Next()
			defer Db.Close()
		}
	}
}
