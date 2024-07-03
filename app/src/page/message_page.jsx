import React, { useState, useEffect } from 'react';
import { Layout } from 'antd';
import BasicLayout from "../component/basic_layout";
import ChatList from "../component/chat_list";
import ChatWindow from "../component/chat_window";
import connectWebSocket from '../service/chat';

const { Sider, Content } = Layout;

export default function MessagePage() {
  const [selectedChat, setSelectedChat] = useState(null);
  const [socket, setSocket] = useState(null);
  const [messages, setMessages] = useState([]);
  const userId = 6 ;//假设当前用户ID为0，根据实际情况调整
  const taskChatId = 1;
  const receiverId = 2;
  useEffect(() => {
    const ws = connectWebSocket({
      userId,
      onopen: () => console.log('WebSocket connection opened'),
      onmessage: (data) => handleIncomingMessage(data),
      onclose: () => console.log('WebSocket connection closed'),
    });

    setSocket(ws);

    return () => {
      if (ws) {
        ws.send({ type: 'unregister', userId }); // 断开连接时注销用户
        ws.close();
      }
    };
  }, []);

  const handleIncomingMessage = (data) => {
    try {
      if (typeof data === 'string') {
        console.log('Received non-JSON message:', data);
        // 根据需要处理非JSON格式的消息
        if (data === 'connected') {
          console.log('WebSocket connected successfully');
        } else {
          console.warn('Unknown message:', data);
        }
      } else {
        const message = JSON.parse(data);
        // 处理接收到的JSON消息，例如更新聊天窗口的消息列表
        console.log('Received message:', message);
        setMessages((prevMessages) => [...prevMessages, message]);
      }
    } catch (error) {
      console.error('Error parsing message:', error);
    }
  };


  return (
    <BasicLayout page='message'>
      <Layout style={{ background: '#fff' }}>
        <Sider width={300} style={{ background: '#fff', borderRight: '1px solid #e8e8e8', overflow: 'auto' }}>
          <ChatList onChatSelect={setSelectedChat} />
        </Sider>
        <Content style={{ padding: '24px', minHeight: 280, display: 'flex', flexDirection: 'column' }}>
          {selectedChat ? (
            <ChatWindow chat={selectedChat} socket={socket} />
          ) : (
            <div style={{ textAlign: 'center', marginTop: '20%' }}>
              <h2>Select a chat to start messaging</h2>
            </div>
          )}
        </Content>
      </Layout>
    </BasicLayout>
  );
}
