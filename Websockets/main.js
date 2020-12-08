var ws = require("ws");
var app = require("express")();

const wsServer = new ws.Server({ noServer: true });

wsServer.on("connection", (socket) => {
  console.log("got connection");
  socket.on("message", (msg) => {
    switch (msg) {
      case "updZone":
        wsServer.clients.forEach(function (client) {
          console.log("client!");
          client.send("getZone");
        });
        break;
      case "updWorker":
        wsServer.clients.forEach(function (client) {
          console.log("client!");
          client.send("getWorker");
        });
        break;
      default:
        console.log("defaulted: " + msg);
    }
  });
});

const server = app.listen(8082);
server.on("upgrade", (request, socket, head) => {
  wsServer.handleUpgrade(request, socket, head, (socket) => {
    wsServer.emit("connection", socket, request);
  });
});