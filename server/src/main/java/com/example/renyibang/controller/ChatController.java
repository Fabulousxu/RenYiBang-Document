package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

  @PostMapping("/enter/{type}/{id}")
  public JSONObject enterChat(@PathVariable String type, @PathVariable long id) {
    long userId = 1;
    return chatService.enterChat(userId, type, id);
  }
}
