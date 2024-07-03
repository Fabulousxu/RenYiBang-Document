package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "service_chat_message")
@Getter
@Setter
@NoArgsConstructor
public class ServiceChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long serviceChatMessageId;

  @ManyToOne
  @JoinColumn(name = "service_chat_id")
  private ServiceChat serviceChat;

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
    json.put("serviceChatMessageId", serviceChatMessageId);
    json.put("serviceChatId", serviceChat.getServiceChatId());
    json.put("senderId", sender.getUserId());
    json.put("receiverId", receiver.getUserId());
    json.put("content", content);
    json.put("images", images);
    json.put("createdAt", createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    return json;
  }
}
