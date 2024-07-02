package com.example.renyibang.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
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
}
