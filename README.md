# Mobile System for Managing Invoice Digitalization

![Codacy Badge](https://app.codacy.com/project/badge/Grade/7a53689b527a47f3845dbedea5541d62)

Projecto realizado para as cadeiras de PAW e PAM do curso de Mestrado em Computação Móvel da Universidade Fernando Pessoa - 2020/2021

Luísa Costa
Rodrigo Soares

## Estrutura

Containers **Docker** via **Docker-Compose**:
- Frontend: NodeJS + React
- Backend: Golang + Gin-gonic + Gorm
- Backend-OCR: Golang + Gorm
- Gestor de Filas de Trabalho: RabbitMQ
- Reverse Proxy: Nginx
- Websockets: NodeJs + WS

## Instruções

### Servidor:

Requer **Docker** instalado na máquina de destino e conecção à internet para descarregar dependências
- Fazer clone do projecto ou descarregar zip
- Abrir consola na pasta raiz e executar o seguinte comando:
> docker-compose up --build
- Abrir a seguinte página:
> http://localhost:5000

### Android:

Requer **Android Studio** para emular/instalar app
 - Fazer clone do projecto ou descarregar zip
 - Abrir pasta *PAWPAM_kotlin* no Android Studio
 - Alterar o ip do servidor no ficheiro para que seja o ip do servidor:
 >  retrofit/ServiceBuilder.kt
 - Compilar e executar a aplicação