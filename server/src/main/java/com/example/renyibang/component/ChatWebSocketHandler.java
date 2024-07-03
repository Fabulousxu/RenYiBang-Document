package com.example.renyibang.component;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.entity.*;
import com.example.renyibang.repository.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
  private final Map<Long, WebSocketSession> userSessionMap = new ConcurrentHashMap<>();
  @Autowired private TaskChatMessageRepository taskChatMessageRepository;
  @Autowired private TaskChatRepository taskChatRepository;
  @Autowired private ServiceChatMessageRepository serviceChatMessageRepository;
  @Autowired private ServiceChatRepository serviceChatRepository;
  @Autowired private UserRepository userRepository;

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    session.sendMessage(new TextMessage("connected"));
    // 前端连接后需要立刻发送一个注册消息
  }

  @Override
  protected void handleTextMessage(WebSocketSession senderSession, TextMessage message)
      throws Exception {
    JSONObject json = JSONObject.parseObject(message.getPayload());
    String type = json.getString("type");

    // 首次连接注册websocket
    if (type.equals("register")) {
      long userId = json.getLongValue("userId");
      if (userRepository.findById(userId).orElse(null) == null) senderSession.close();
      userSessionMap.put(userId, senderSession);
      return;
    }

    long senderId = json.getLongValue("senderId");
    long receiverId = json.getLongValue("receiverId");
    User sender = userRepository.findById(senderId).orElse(null);
    User receiver = userRepository.findById(receiverId).orElse(null);
    if (sender == null || receiver == null) return;
    WebSocketSession receiverSession = userSessionMap.get(receiverId);
    String content = json.getString("content");
    String images = "";
    //    for (Object image : json.getJSONArray("images")) images += " " + image.toString();

    if (type.equals("task")) {
      // 消息存储
      TaskChatMessage taskChatMessage = new TaskChatMessage();
      TaskChat taskChat = taskChatRepository.findById(json.getLongValue("taskChatId")).orElse(null);
      if (taskChat == null) return;
      taskChatMessage.setTaskChat(taskChat);
      taskChatMessage.setContent(content);
      taskChatMessage.setImages(images);
      taskChatMessage.setSender(sender);
      taskChatMessage.setReceiver(receiver);
      taskChatMessageRepository.save(taskChatMessage);

      // 消息发送
      if (receiverSession == null || !receiverSession.isOpen()) return;
      receiverSession.sendMessage(new TextMessage(taskChatMessage.toJSON().toString()));
      senderSession.sendMessage(new TextMessage(taskChatMessage.toJSON().toString()));
    } else if (type.equals("service")) {
      // 消息储存
      ServiceChatMessage serviceChatMessage = new ServiceChatMessage();
      ServiceChat serviceChat =
          serviceChatRepository.findById(json.getLongValue("serviceChatId")).orElse(null);
      if (serviceChat == null) return;
      serviceChatMessage.setServiceChat(serviceChat);
      serviceChatMessage.setContent(content);
      serviceChatMessage.setImages(images);
      serviceChatMessage.setSender(sender);
      serviceChatMessage.setReceiver(receiver);
      serviceChatMessageRepository.save(serviceChatMessage);

      // 消息发送
      if (receiverSession == null || !receiverSession.isOpen()) return;
      receiverSession.sendMessage(new TextMessage(serviceChatMessage.toJSON().toString()));
      senderSession.sendMessage(new TextMessage(serviceChatMessage.toJSON().toString()));
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    for (Map.Entry<Long, WebSocketSession> entry : userSessionMap.entrySet()) {
      if (entry.getValue().equals(session)) {
        userSessionMap.remove(entry.getKey());
        break;
      }
    }
  }
}
