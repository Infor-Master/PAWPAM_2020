FROM golang:1.13-alpine

ENV GOPATH /go

RUN apk update && apk upgrade && \
    apk add --no-cache bash git openssh gcc libc-dev

RUN apk add \
  g++ \
  git \
  musl-dev \
  tesseract-ocr-dev

RUN go get -u github.com/jinzhu/gorm
RUN go get -u github.com/jinzhu/gorm/dialects/postgres
RUN go get -u github.com/alecthomas/template
RUN go get -u github.com/rs/cors/wrapper/gin
RUN go get -u github.com/otiai10/gosseract
RUN go get github.com/streadway/amqp/...

# HOT RELOAD
RUN go get -u github.com/githubnemo/CompileDaemon

# Set the Current Working Directory inside the container
WORKDIR /go/src/projetoapi

# Copy everything from the current directory to the Working Directory inside the container
COPY . .

# Build the Go app
RUN go build -o main .

# Run the executable DEPLOYMENT
# CMD ./main

# HOT RELOAD
ENTRYPOINT CompileDaemon -log-prefix=false -build="go build ./main.go" -command="./main"