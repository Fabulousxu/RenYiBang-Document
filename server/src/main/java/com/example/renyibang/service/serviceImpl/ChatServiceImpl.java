package com.example.renyibang.service.serviceImpl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.entity.*;
import com.example.renyibang.repository.*;
import com.example.renyibang.service.ChatService;
import com.example.renyibang.util.ResponseUtil;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  @Autowired private TaskChatRepository taskChatRepository;
  @Autowired private ServiceChatRepository serviceChatRepository;
  @Autowired private TaskChatMessageRepository taskChatMessageRepository;
  @Autowired private ServiceChatMessageRepository serviceChatMessageRepository;
  @Autowired private UserRepository userRepository;

  @Override
  public JSONObject getChatList(long userId) {
    List<TaskChat> taskChats =
        taskChatRepository.findByChatter_UserIdOrTask_Owner_UserIdOrderByLastTimeDesc(
            userId, userId);
    List<ServiceChat> serviceChats =
        serviceChatRepository.findByChatter_UserIdOrService_Owner_UserIdOrderByLastTimeDesc(
            userId, userId);

    System.out.println(taskChats.size());

    User self = userRepository.findById(userId).orElse(null);
    if (self == null) return ResponseUtil.error("用户不存在");

    JSONArray chatArray = new JSONArray();
    int i = 0, j = 0;
    while (i < taskChats.size() && j < serviceChats.size()) {
      TaskChat taskChat = taskChats.get(i);
      ServiceChat serviceChat = serviceChats.get(j);
      JSONObject chat = new JSONObject();
      User chatter = null;
      if (taskChat.getLastTime().compareTo(serviceChat.getLastTime()) > 0) {
        chat.put("type", "task");
        chat.put("chatId", taskChat.getTaskChatId());
        chat.put("lastTime", taskChat.getLastTime().format(formatter));
        chat.put("lastMessage", taskChat.getLastMessage());
        if (userId == taskChat.getChatter().getUserId()) chatter = taskChat.getTask().getOwner();
        else chatter = taskChat.getChatter();
        if (userId == taskChat.getLastChatter().getUserId()) chat.put("unread", 0);
        else chat.put("unread", taskChat.getUnread());
        i++;
      } else {
        chat.put("type", "service");
        chat.put("chatId", serviceChat.getServiceChatId());
        chat.put("lastTime", serviceChat.getLastTime().format(formatter));
        chat.put("lastMessage", serviceChat.getLastMessage());
        if (userId == serviceChat.getChatter().getUserId())
          chatter = serviceChat.getService().getOwner();
        else chatter = serviceChat.getChatter();
        if (userId == serviceChat.getLastChatter().getUserId()) chat.put("unread", 0);
        else chat.put("unread", serviceChat.getUnread());
        j++;
      }
      chat.put("chatter", chatter.toJSON());
      chatArray.add(chat);
    }
    while (i < taskChats.size()) {
      TaskChat taskChat = taskChats.get(i);
      JSONObject chat = new JSONObject();
      chat.put("type", "task");
      chat.put("chatId", taskChat.getTaskChatId());
      chat.put("lastTime", taskChat.getLastTime().format(formatter));
      chat.put("lastMessage", taskChat.getLastMessage());
      User chatter = null;
      if (userId == taskChat.getChatter().getUserId()) chatter = taskChat.getTask().getOwner();
      else chatter = taskChat.getChatter();
      if (userId == taskChat.getLastChatter().getUserId()) chat.put("unread", 0);
      else chat.put("unread", taskChat.getUnread());
      chat.put("chatter", chatter.toJSON());
      chatArray.add(chat);
      i++;
    }
    while (j < serviceChats.size()) {
      ServiceChat serviceChat = serviceChats.get(j);
      JSONObject chat = new JSONObject();
      chat.put("type", "service");
      chat.put("chatId", serviceChat.getServiceChatId());
      chat.put("lastTime", serviceChat.getLastTime().format(formatter));
      chat.put("lastMessage", serviceChat.getLastMessage());
      User chatter = null;
      if (userId == serviceChat.getChatter().getUserId())
        chatter = serviceChat.getService().getOwner();
      else chatter = serviceChat.getChatter();
      if (userId == serviceChat.getLastChatter().getUserId()) chat.put("unread", 0);
      else chat.put("unread", serviceChat.getUnread());
      chat.put("chatter", chatter.toJSON());
      chatArray.add(chat);
      j++;
    }

    JSONObject data = new JSONObject();
    data.put("self", self.toJSON());
    data.put("chats", chatArray);
    return ResponseUtil.success(data);
  }

  @Override
  public JSONObject getChatHistory(
      long userId, String type, long chatId, long lastMessageId, int count) {
    if (type.equals("task")) {
      TaskChat taskChat = taskChatRepository.findById(chatId).orElse(null);
      if (taskChat == null) return ResponseUtil.error("聊天不存在");
      if (taskChat.getChatter().getUserId() != userId
          && taskChat.getTask().getOwner().getUserId() != userId)
        return ResponseUtil.error("无权查看聊天记录");
      if (taskChat.getUnread() > 0 && taskChat.getLastChatter().getUserId() != userId) {
        taskChat.setUnread(0);
        taskChatRepository.save(taskChat);
      }
      JSONArray history = new JSONArray();
      Page<TaskChatMessage> taskChatMessages =
          taskChatMessageRepository
              .findByTaskChat_TaskChatIdAndTaskChatMessageIdLessThanOrderByCreatedAtDesc(
                  chatId, lastMessageId, PageRequest.of(0, count));
      for (TaskChatMessage taskChatMessage : taskChatMessages) {
        JSONObject message = new JSONObject();
        message.put("messageId", taskChatMessage.getTaskChatMessageId());
        message.put("senderId", taskChatMessage.getSender().getUserId());
        message.put("content", taskChatMessage.getContent());
        message.put("createdAt", taskChatMessage.getCreatedAt().format(formatter));
        history.add(message);
      }
      return ResponseUtil.success(history);
    } else {
      ServiceChat serviceChat = serviceChatRepository.findById(chatId).orElse(null);
      if (serviceChat == null) return ResponseUtil.error("聊天不存在");
      if (serviceChat.getChatter().getUserId() != userId
          && serviceChat.getService().getOwner().getUserId() != userId)
        return ResponseUtil.error("无权查看聊天记录");
      if (serviceChat.getUnread() > 0 && serviceChat.getLastChatter().getUserId() != userId) {
        serviceChat.setUnread(0);
        serviceChatRepository.save(serviceChat);
      }
      JSONArray history = new JSONArray();
      Page<ServiceChatMessage> serviceChatMessages =
          serviceChatMessageRepository
              .findByServiceChat_ServiceChatIdAndServiceChatMessageIdLessThanOrderByCreatedAtDesc(
                  chatId, lastMessageId, PageRequest.of(0, count));
      for (ServiceChatMessage serviceChatMessage : serviceChatMessages) {
        JSONObject message = new JSONObject();
        message.put("messageId", serviceChatMessage.getServiceChatMessageId());
        message.put("senderId", serviceChatMessage.getSender().getUserId());
        message.put("content", serviceChatMessage.getContent());
        message.put("createdAt", serviceChatMessage.getCreatedAt().format(formatter));
        history.add(message);
      }
      return ResponseUtil.success(history);
    }
  }
}
