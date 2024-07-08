package com.renyibang.chatapi.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.renyibang.chatapi.dao.ChatRepository;
import com.renyibang.chatapi.entity.Chat;
import com.renyibang.chatapi.service.ChatService;
import com.renyibang.chatapi.util.ResponseUtil;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  @Autowired private ChatRepository chatRepository;

  private static JSONObject getChatJson(Chat chat, long userId) {
    long chatterId = chat.getOfOwnerId() == userId ? chat.getChatterId() : chat.getOfOwnerId();
    /* ********** to do: fetch chatter info ********** */
    JSONObject chatterJson = new JSONObject();
    /* *********************************************** */
    JSONObject chatJson = new JSONObject();
    chatJson.put("chatId", chat.getChatId());
    chatJson.put("type", chat.getType() == 0 ? "task" : "service");
    chatJson.put("unread", userId == chat.getLastMessageSenderId() ? 0 : chat.getUnreadCount());
    chatJson.put("lastMessage", chat.getLastMessageContent());
    chatJson.put("lastTime", chat.getLastMessageCreatedAt().format(formatter));
    chatJson.put("chatter", chatterJson);
    return chatJson;
  }

  @Override
  public JSONObject getChats(long userId) {
    List<Chat> chats = chatRepository.findByOfOwnerIdOrChatterId(userId, userId);
    chats.sort((a, b) -> b.getLastMessageCreatedAt().compareTo(a.getLastMessageCreatedAt()));
    JSONArray chatArray = new JSONArray();
    for (Chat chat : chats) chatArray.add(getChatJson(chat, userId));
    JSONObject data = new JSONObject();
    data.put("chats", chatArray);
    /* ********** to do: fetch chatter info ********** */
    JSONObject selfJson = new JSONObject();
    /* *********************************************** */
    data.put("self", selfJson);
    return ResponseUtil.success(data);
  }

  @Override
  public JSONObject initiateChat(long userId, byte type, long ofId) {
    Chat chat = chatRepository.findByTypeAndOfIdAndChatterId(type, ofId, userId).orElse(null);
    if (chat == null) {
      /* ********** to do: fetch ofOwner id ********** */
      long ofOwnerId = 2;
      /* ********************************************* */
      chat = new Chat();
      chat.setType(type);
      chat.setOfId(ofId);
      chat.setOfOwnerId(ofOwnerId);
      chat.setChatterId(userId);
      chat.setLastMessageSenderId(userId);
      chatRepository.save(chat);
    }
    JSONObject data = new JSONObject();
    data.put("chatId", chat.getChatId());
    return ResponseUtil.success(data);
  }
}
