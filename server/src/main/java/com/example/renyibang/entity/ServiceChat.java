package com.example.renyibang.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "service_chat")
@Getter
@Setter
@NoArgsConstructor
public class ServiceChat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long serviceChatId;

  @ManyToOne
  @JoinColumn(name = "service_id")
  private Service service;

  @ManyToOne
  @JoinColumn(name = "chatter_id")
  private User chatter;

  private int unread;
  private String lastMessage;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime lastTime;

  @ManyToOne
  @JoinColumn(name = "last_chatter_id")
  private User lastChatter;

  @OneToMany(mappedBy = "serviceChat")
  @OrderBy("createdAt DESC")
  private List<ServiceChatMessage> messages;
}
