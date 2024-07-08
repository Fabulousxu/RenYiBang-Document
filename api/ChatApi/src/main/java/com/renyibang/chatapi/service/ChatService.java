package com.renyibang.chatapi.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {
  JSONObject getChats(long userId);

  JSONObject initiateChat(long userId, byte type, long ofId);
}
