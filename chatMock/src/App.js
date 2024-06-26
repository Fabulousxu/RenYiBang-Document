// src/App.js
import React from 'react';
import Chat from './service/Chat';
import { useState } from 'react';

function App() {
  const [username, setUsername] = useState('');
  const [isChatting, setIsChatting] = useState(false);

  const handleStartChat = () => {
      if (username.trim()) {
          setIsChatting(true);
      }
  };

  return (
      <div className="App">
          {isChatting ? (
              <Chat username={username} />
          ) : (
              <div>
                  <h2>Enter your username to start chatting</h2>
                  <input value={username} onChange={(e) => setUsername(e.target.value)} />
                  <button onClick={handleStartChat}>Start Chat</button>
              </div>
          )}
      </div>
  );
}

export default App;