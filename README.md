# Mobile System for Managing Invoice Digitalization
![Codacy Badge](https://app.codacy.com/project/badge/Grade/7a53689b527a47f3845dbedea5541d62)

Projecto realizado para as cadeiras de PAW e PAM do curso de Mestrado em Computação Móvel da Universidade Fernando Pessoa - 2020 

Luísa Costa
Rodrigo Soares

## Estrutura
Containers **Docker** via **Docker-Compose**:
-  Frontend: NodeJS +  React
-  Backend: Golang + Gin-gonic + Gorm
-  Reverse Proxy: Nginx
-  Websockets: NodeJs + WS

## Instruções

Requer **Docker** instalado na máquina de destino e conecção à internet para descarregar dependências

-  Fazer clone do projecto ou descarregar zip
-  Abrir consola na pasta raiz e executar o seguinte comando:
  > docker-compose up
-  Abrir a seguinte página:
  > http://localhost:5000