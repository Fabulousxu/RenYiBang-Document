// server.js
const WebSocket = require('ws');

const wss = new WebSocket.Server({ port: 8080 });

wss.on('connection', ws => {
    ws.on('message', message => {
        const parsedMessage = JSON.parse(message);
        // 广播消息给所有连接的客户端
        wss.clients.forEach(client => {
            if (client.readyState === WebSocket.OPEN) {
                client.send(JSON.stringify(parsedMessage));
            }
        });
    });

    ws.send(JSON.stringify({ user: 'Server', text: 'Welcome to the chat!' }));
});

console.log('WebSocket server is running on ws://localhost:8080');
