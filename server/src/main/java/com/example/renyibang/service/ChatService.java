package com.example.renyibang.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {
  JSONObject getChatList(long userId);

  JSONObject getChatHistory(long userId, String type, long chatId, long lastMessageId, int count);

  JSONObject enterChat(long userId, String type, long id);
}
