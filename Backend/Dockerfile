FROM golang:1.13-alpine

RUN apk update && apk upgrade && \
    apk add --no-cache bash git openssh gcc libc-dev

RUN go get -u github.com/jinzhu/gorm
RUN go get -u github.com/gin-gonic/gin
RUN go get -u github.com/jinzhu/gorm/dialects/postgres
RUN go get -u github.com/swaggo/gin-swagger
RUN go get -u github.com/swaggo/swag/cmd/swag
RUN go get -u github.com/alecthomas/template
RUN go get -u github.com/swaggo/files
RUN go get -u github.com/swaggo/http-swagger
RUN go get -u github.com/gin-contrib/cors
RUN go get -u github.com/rs/cors/wrapper/gin
RUN go get -u github.com/dgrijalva/jwt-go
RUN go get -u github.com/stretchr/testify
RUN go get -u github.com/qor/media/filesystem
# HOT RELOAD
RUN go get -u github.com/githubnemo/CompileDaemon


# Set the Current Working Directory inside the container
WORKDIR /go/src/projetoapi

# Copy everything from the current directory to the Working Directory inside the container
COPY . .

# RUN Swagger
RUN swag init

# Build the Go app
RUN go build -o main .

# Expose port 8081 to the outside world
EXPOSE 8081

# Run the executable DEPLOYMENT
# CMD ./main

# HOT RELOAD
ENTRYPOINT CompileDaemon -log-prefix=false -build="go build ./main.go" -command="./main"