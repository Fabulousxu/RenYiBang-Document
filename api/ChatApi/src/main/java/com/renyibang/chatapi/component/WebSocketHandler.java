package com.renyibang.chatapi.component;

import com.alibaba.fastjson2.JSONObject;
import com.renyibang.chatapi.dao.ChatRepository;
import com.renyibang.chatapi.dao.MessageRepository;
import com.renyibang.chatapi.entity.Chat;
import com.renyibang.chatapi.entity.Message;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private final Map<Long, List<WebSocketSession>> userSessionMap = new ConcurrentHashMap<>();
  private final Map<WebSocketSession, Long> sessionUserMap = new ConcurrentHashMap<>();
  @Autowired private ChatRepository chatRepository;
  @Autowired private MessageRepository messageRepository;

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    JSONObject json = JSONObject.parseObject(message.getPayload());
    if (json.getString("type").equals("register")) {
      long userId = json.getLongValue("userId");
      List<WebSocketSession> userSessionList =
          userSessionMap.getOrDefault(userId, new CopyOnWriteArrayList<>());
      userSessionList.add(session);
      userSessionMap.put(userId, userSessionList);
      sessionUserMap.put(session, userId);
      return;
    }

    String chatId = json.getString("chatId");
    Chat chat = chatRepository.findById(chatId).orElse(null);
    if (chat == null) return;
    long senderId = sessionUserMap.get(session);
    long receiverId = senderId == chat.getOfOwnerId() ? chat.getChatterId() : chat.getOfOwnerId();
    List<WebSocketSession> senderSessionList = userSessionMap.get(senderId);
    List<WebSocketSession> receiverSessionList =
        userSessionMap.getOrDefault(receiverId, new CopyOnWriteArrayList<>());
    Message chatMessage = new Message();
    chatMessage.setChatId(chatId);
    chatMessage.setSenderId(senderId);
    chatMessage.setContent(json.getString("content"));
    messageRepository.save(chatMessage);
    if (receiverSessionList.isEmpty()) chat.setUnreadCount(chat.getUnreadCount() + 1);
    chat.setLastMessageSenderId(senderId);
    chat.setLastMessageContent(chatMessage.getContent());
    chat.setLastMessageCreatedAt(chatMessage.getCreatedAt());
    chatRepository.save(chat);

    JSONObject messageJson = new JSONObject();
    messageJson.put("chatMessageId", chatMessage.getMessageId());
    messageJson.put("chatId", chatId);
    messageJson.put("senderId", senderId);
    messageJson.put("content", chatMessage.getContent());
    messageJson.put("createdAt", chatMessage.getCreatedAt().format(formatter));
    for (WebSocketSession senderSession : senderSessionList)
      senderSession.sendMessage(new TextMessage(messageJson.toString()));
    for (WebSocketSession receiverSession : receiverSessionList)
      receiverSession.sendMessage(new TextMessage(messageJson.toString()));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    long userId = sessionUserMap.get(session);
    List<WebSocketSession> userSessionList = userSessionMap.get(userId);
    userSessionList.remove(session);
    if (userSessionList.isEmpty()) userSessionMap.remove(userId);
    sessionUserMap.remove(session);
  }
}
