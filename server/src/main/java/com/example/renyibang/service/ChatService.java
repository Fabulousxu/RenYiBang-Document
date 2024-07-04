package com.example.renyibang.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {
  JSONObject getChatList(long userId);
}
