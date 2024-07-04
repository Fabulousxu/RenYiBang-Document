package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
  @Autowired private ChatService chatService;

  @GetMapping("/list")
  public JSONObject getChatList() {
    long userId = 1;
    return chatService.getChatList(userId);
  }

  @GetMapping("/history")
  public JSONObject getChatHistory(String type, long chatId, long lastMessageId, int count) {
    long userId = 1;
    return chatService.getChatHistory(userId, type, chatId, lastMessageId, count);
  }
}
