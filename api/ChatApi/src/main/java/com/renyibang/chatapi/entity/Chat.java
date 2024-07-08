package com.renyibang.chatapi.entity;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Document("chat")
public class Chat {
  @MongoId private String chatId;
  @Indexed private byte type; // 0: task, 1: service
  @Indexed private long ofId; // task or service id
  @Indexed private long ofOwnerId; // task or service owner id
  @Indexed private long chatterId; // chatter id
  private int unreadCount = 0;
  private long lastMessageSenderId;
  private String lastMessageContent = "";

  @CreatedDate
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  @Indexed
  private LocalDateTime lastMessageCreatedAt;
}
