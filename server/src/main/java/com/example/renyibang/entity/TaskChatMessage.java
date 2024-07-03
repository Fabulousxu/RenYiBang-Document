package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "task_chat_message")
@Getter
@Setter
public class TaskChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long taskChatMessageId;

  @ManyToOne
  @JoinColumn(name = "task_chat_id")
  private TaskChat taskChat;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private User sender;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  private User receiver;

  private String content;
  private String images;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  public JSONObject toJSON() {
    JSONObject json = new JSONObject();
    json.put("taskChatMessageId", taskChatMessageId);
    json.put("taskChatId", taskChat.getTaskChatId());
    json.put("senderId", sender.getUserId());
    json.put("receiverId", receiver.getUserId());
    json.put("content", content);
    json.put("images", images);
    json.put("createdAt", createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    return json;
  }
}
