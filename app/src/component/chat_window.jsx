import React, { useEffect, useRef } from 'react';
import { Card, List, Input, Button, Avatar } from 'antd';

const ChatWindow = ({ chat }) => {
  const [message, setMessage] = React.useState('');
  const [messages, setMessages] = React.useState([
    { sender: 'User 1', content: 'Hello!', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=1' },
    { sender: 'Me', content: 'Hi, how are you?', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=2' },
    { sender: 'User 1', content: 'I am good, thank you! How about you?', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=1' },
    { sender: 'User 1', content: 'Hello!', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=1' },
    { sender: 'Me', content: 'Hi, how are you?', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=2' },
    { sender: 'User 1', content: 'I am good, thank you! How about you?', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=1' },
    { sender: 'User 1', content: 'Hello!', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=1' },
    { sender: 'Me', content: 'Hi, how are you?', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=2' },
    { sender: 'User 1', content: 'I am good, thank you! How about you?', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=1' },
    { sender: 'User 1', content: 'Hello!', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=1' },
    { sender: 'Me', content: 'Hi, how are you?', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=2' },
    { sender: 'User 1', content: 'I am good, thank you! How about you?', avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=1' },
    // Add more initial messages if needed
  ]);

  const messagesEndRef = useRef(null);

  const handleSend = () => {
    if (message) {
      setMessages([...messages, { sender: 'Me', content: message, avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=2' }]);
      setMessage('');
    }
  };

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  return (
    <Card title={`Chat with ${chat.title}`} style={{ height: 'calc(67vh)', display: 'flex', flexDirection: 'column' }}>
      <div style={{ flex: 1, overflowY: 'auto', padding: '10px', height: 'calc(55vh)' }}>
        <List
          dataSource={messages}
          renderItem={(item) => (
            <div style={{
              display: 'flex',
              marginBottom: '10px',
              justifyContent: item.sender === 'Me' ? 'flex-end' : 'flex-start'
            }}>
              {item.sender !== 'Me' && <Avatar src={item.avatar} style={{ marginRight: '10px' }} />}
              <div style={{
                maxWidth: '60%',
                backgroundColor: item.sender === 'Me' ? '#1677FF' : '#f1f0f0',
                color: item.sender === 'Me' ? '#fff' : '#000',
                padding: '10px',
                borderRadius: '15px',
                wordWrap: 'break-word'
              }}>
                {item.content}
              </div>
              {item.sender === 'Me' && <Avatar src={item.avatar} style={{ marginLeft: '10px' }} />}
            </div>
          )}
        />
        <div ref={messagesEndRef} />
      </div>

      <div style={{
        padding: '10px',
        borderTop: '1px solid #e8e8e8',
        background: '#fff',
        position: 'fixed',
        bottom: '5%',
        width: 'calc(100% - 330px)', // Adjust width to match the layout
        boxSizing: 'border-box'
      }}>
        <Input
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          placeholder="Type a message"
          onPressEnter={handleSend}
          style={{ flexGrow: 1, marginRight: '10px', width: '60%' }}
        />
        <Button type="primary" onClick={handleSend}>
          Send
        </Button>
      </div>
    </Card>
  );
};

export default ChatWindow;
