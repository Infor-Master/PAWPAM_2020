const { request } = require("express");
var ws = require("ws");
var app = require("express")();

const wsServer = new ws.Server({ noServer: true });

wsServer.on("connection", (socket) => {
  console.log("got connection");

  socket.on("message", (msg) => {
    const dataFromClient = JSON.parse(msg);

    switch (dataFromClient.type) {
      case "userevent":
        socket.send(JSON.stringify({
          data: dataFromClient.data,
          type: "serverevent"
        }));
        break;
      default:
        console.log("defaulted: " + dataFromClient);
    }
  });
});

const server = app.listen(8082);
server.on("upgrade", (request, socket, head) => {
  wsServer.handleUpgrade(request, socket, head, (socket) => {
    wsServer.emit("connection", socket, request);
  });
});





/* const clients = {}

// This code generates unique userid for everyuser.
const getUniqueID = () => {
  const s4 = () => Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
  return s4() + s4() + '-' + s4();
} */

/* wsServer.on('request', (request) => {
  var userID = getUniqueID();
  console.log("Received new websocket connection from origin: " + request.origin)

  const connection = request.accept(null, request.origin);
  clients[userID] = connection;
  console.log("Connected: " + userID)
}); */