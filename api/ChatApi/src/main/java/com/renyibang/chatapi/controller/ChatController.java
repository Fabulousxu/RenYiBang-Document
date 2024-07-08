package com.renyibang.chatapi.controller;

import com.alibaba.fastjson2.JSONObject;
import com.renyibang.chatapi.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
  @Autowired private ChatService chatService;

  @GetMapping("/list")
  public JSONObject getChats() {
    long userId = 1;
    return chatService.getChats(userId);
  }

  @PostMapping("/enter/{type}/{id}")
  public JSONObject initiateChat(@PathVariable String type, @PathVariable long id) {
    long userId = 1;
    return chatService.initiateChat(userId, (byte) (type.equals("task") ? 0 : 1), id);
  }
}
