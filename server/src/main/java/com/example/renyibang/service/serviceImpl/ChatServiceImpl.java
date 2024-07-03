package com.example.renyibang.service.serviceImpl;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.entity.ServiceChat;
import com.example.renyibang.entity.TaskChat;
import com.example.renyibang.repository.*;
import com.example.renyibang.service.ChatService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
  @Autowired private TaskChatRepository taskChatRepository;
  @Autowired private ServiceChatRepository serviceChatRepository;

  @Override
  public JSONObject getChatList(long userId) {
    List<TaskChat> taskChats =
        taskChatRepository.findByChatter_UserIdOrTask_Owner_UserIdOrderByLastTimeDesc(
            userId, userId);
    List<ServiceChat> serviceChats =
        serviceChatRepository.findByChatter_UserIdOrService_Owner_UserIdOrderByLastTimeDesc(
            userId, userId);

    return null;
  }
}
