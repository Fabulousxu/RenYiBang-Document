// src/Chat.js
import React, { useState, useEffect } from 'react';

function Chat({ username }) {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState('');
    const [ws, setWs] = useState(null);

    useEffect(() => {
        const socket = new WebSocket('ws://localhost:8080/chat');
        
        socket.onopen = () => {
            console.log(`${username} WebSocket connection established`);
        };

        socket.onmessage = (event) => {
            const message = JSON.parse(event.data);
            setMessages((prevMessages) => [...prevMessages, message]);
        };

        setWs(socket);

        return () => socket.close();
    }, [username]);

    const sendMessage = () => {
        if (ws && input.trim()) {
            const message = { user: username, text: input };
            ws.send(JSON.stringify(message));
            setInput('');
        }
    };

    return (
        <div>
            <h3>{username}</h3>
            <div style={{ border: '1px solid black', padding: '10px', height: '200px', overflowY: 'scroll' }}>
                {messages.map((message, index) => (
                    <div key={index}><strong>{message.user}:</strong> {message.text}</div>
                ))}
            </div>
            <input value={input} onChange={(e) => setInput(e.target.value)} />
            <button onClick={sendMessage}>Send</button>
        </div>
    );
}

export default Chat;
