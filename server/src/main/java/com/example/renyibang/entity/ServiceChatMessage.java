package com.example.renyibang.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
}
