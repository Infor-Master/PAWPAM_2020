// GENERATED BY THE COMMAND ABOVE; DO NOT EDIT
// This file was generated by swaggo/swag

package docs

import (
	"bytes"
	"encoding/json"
	"strings"

	"github.com/alecthomas/template"
	"github.com/swaggo/swag"
)

var doc = `{
    "schemes": {{ marshal .Schemes }},
    "swagger": "2.0",
    "info": {
        "description": "{{.Description}}",
        "title": "{{.Title}}",
        "contact": {},
        "version": "{{.Version}}"
    },
    "host": "{{.Host}}",
    "basePath": "{{.BasePath}}",
    "paths": {
        "/auth/login": {
            "post": {
                "description": "Autentica o utilizador e gera o token para os próximos acessos",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "summary": "Realizar autenticação",
                "parameters": [
                    {
                        "description": "Do login",
                        "name": "evaluation",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "$ref": "#/definitions/model.Users"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/model.Claims"
                        }
                    },
                    "400": {
                        "description": "Bad request"
                    },
                    "401": {
                        "description": "Unauthorized"
                    }
                }
            }
        },
        "/auth/refresh_token": {
            "put": {
                "description": "Atualiza o token de autenticação do usuário",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "summary": "Atualiza token de autenticação",
                "parameters": [
                    {
                        "type": "string",
                        "description": "Token",
                        "name": "Authorization",
                        "in": "header",
                        "required": true
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/model.Claims"
                        }
                    },
                    "400": {
                        "description": "Bad request"
                    },
                    "401": {
                        "description": "Unauthorized"
                    }
                }
            }
        },
        "/auth/register": {
            "post": {
                "description": "Regista um utilizador",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "summary": "Realizar registro",
                "parameters": [
                    {
                        "description": "Do register",
                        "name": "evaluation",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "$ref": "#/definitions/model.Users"
                        }
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/model.Claims"
                        }
                    },
                    "400": {
                        "description": "Bad request"
                    },
                    "401": {
                        "description": "Unauthorized"
                    }
                }
            }
        },
        "/echo": {
            "get": {
                "description": "Echo the data sent though the get request.",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "summary": "Echo the data sent on get",
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "array",
                            "items": {
                                "$ref": "#/definitions/model.Evaluation"
                            }
                        }
                    },
                    "404": {
                        "description": "Not found"
                    }
                }
            }
        },
        "/evaluation": {
            "get": {
                "security": [
                    {
                        "BearerAuth": []
                    }
                ],
                "description": "Exibe a lista, sem todos os campos, de todas as avaliações",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "summary": "Recupera as avaliações",
                "parameters": [
                    {
                        "type": "string",
                        "description": "Token",
                        "name": "Authorization",
                        "in": "header",
                        "required": true
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "type": "array",
                            "items": {
                                "$ref": "#/definitions/model.Evaluation"
                            }
                        }
                    },
                    "404": {
                        "description": "Not found"
                    }
                }
            },
            "post": {
                "security": [
                    {
                        "BearerAuth": []
                    }
                ],
                "description": "Cria uma avaliação sobre a utilização da aplicação",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "summary": "Adicionar uma avaliação",
                "parameters": [
                    {
                        "type": "string",
                        "description": "Token",
                        "name": "Authorization",
                        "in": "header",
                        "required": true
                    },
                    {
                        "description": "Add evaluation",
                        "name": "evaluation",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "$ref": "#/definitions/model.Evaluation"
                        }
                    }
                ],
                "responses": {
                    "201": {
                        "description": "Created",
                        "schema": {
                            "$ref": "#/definitions/model.Evaluation"
                        }
                    },
                    "400": {
                        "description": "Bad request"
                    },
                    "404": {
                        "description": "Not found"
                    }
                }
            }
        },
        "/evaluation/{id}": {
            "get": {
                "security": [
                    {
                        "BearerAuth": []
                    }
                ],
                "description": "Exibe os detalhes de uma avaliação pelo ID",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "summary": "Recupera uma avaliação pelo id",
                "operationId": "get-evaluation-by-int",
                "parameters": [
                    {
                        "type": "string",
                        "description": "Token",
                        "name": "Authorization",
                        "in": "header",
                        "required": true
                    },
                    {
                        "type": "integer",
                        "description": "Evaluation ID",
                        "name": "id",
                        "in": "path",
                        "required": true
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/model.Evaluation"
                        }
                    },
                    "404": {
                        "description": "Not found"
                    }
                }
            },
            "put": {
                "security": [
                    {
                        "BearerAuth": []
                    }
                ],
                "description": "Atualiza uma avaliação sobre a utilização da aplicação",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "summary": "Atualiza uma avaliação",
                "parameters": [
                    {
                        "type": "string",
                        "description": "Token",
                        "name": "Authorization",
                        "in": "header",
                        "required": true
                    },
                    {
                        "description": "Udpdate evaluation",
                        "name": "evaluation",
                        "in": "body",
                        "required": true,
                        "schema": {
                            "$ref": "#/definitions/model.Evaluation"
                        }
                    },
                    {
                        "type": "integer",
                        "description": "Evaluation ID",
                        "name": "id",
                        "in": "path",
                        "required": true
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/model.Evaluation"
                        }
                    },
                    "400": {
                        "description": "Bad request"
                    },
                    "404": {
                        "description": "Not found"
                    }
                }
            },
            "delete": {
                "security": [
                    {
                        "BearerAuth": []
                    }
                ],
                "description": "Exclui uma avaliação realizada",
                "consumes": [
                    "application/json"
                ],
                "produces": [
                    "application/json"
                ],
                "summary": "Exclui uma avaliação pelo ID",
                "operationId": "get-string-by-int",
                "parameters": [
                    {
                        "type": "string",
                        "description": "Token",
                        "name": "Authorization",
                        "in": "header",
                        "required": true
                    },
                    {
                        "type": "integer",
                        "description": "Evaluation ID",
                        "name": "id",
                        "in": "path",
                        "required": true
                    }
                ],
                "responses": {
                    "200": {
                        "description": "OK",
                        "schema": {
                            "$ref": "#/definitions/model.Evaluation"
                        }
                    },
                    "404": {
                        "description": "Not found"
                    }
                }
            }
        }
    },
    "definitions": {
        "model.Claims": {
            "type": "object",
            "properties": {
                "username": {
                    "type": "string"
                }
            }
        },
        "model.Evaluation": {
            "type": "object",
            "properties": {
                "Note": {
                    "type": "string"
                },
                "Rating": {
                    "type": "integer"
                }
            }
        },
        "model.Users": {
            "type": "object",
            "properties": {
                "password": {
                    "type": "string"
                },
                "username": {
                    "type": "string"
                }
            }
        }
    }
}`

type swaggerInfo struct {
	Version     string
	Host        string
	BasePath    string
	Schemes     []string
	Title       string
	Description string
}

// SwaggerInfo holds exported Swagger Info so clients can modify it
var SwaggerInfo = swaggerInfo{
	Version:     "",
	Host:        "",
	BasePath:    "",
	Schemes:     []string{},
	Title:       "",
	Description: "",
}

type s struct{}

func (s *s) ReadDoc() string {
	sInfo := SwaggerInfo
	sInfo.Description = strings.Replace(sInfo.Description, "\n", "\\n", -1)

	t, err := template.New("swagger_info").Funcs(template.FuncMap{
		"marshal": func(v interface{}) string {
			a, _ := json.Marshal(v)
			return string(a)
		},
	}).Parse(doc)
	if err != nil {
		return doc
	}

	var tpl bytes.Buffer
	if err := t.Execute(&tpl, sInfo); err != nil {
		return doc
	}

	return tpl.String()
}

func init() {
	swag.Register(swag.Name, &s{})
}
