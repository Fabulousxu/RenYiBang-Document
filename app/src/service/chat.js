const connectWebSocket = ({userId, onopen, onmessage, onclose}) => {
  const socket = new WebSocket('ws://localhost:8080/chat')

  socket.on('open', () => {
    socket.send(JSON.stringify({type: 'register', userId: userId}))
    onopen()
  })

  socket.on('message', data => {
    onmessage(data)
  })

  socket.on('close', () => {
    onclose()
  })

  return {
    send: message => {
      socket.send(JSON.stringify(message))
    }
  }
}