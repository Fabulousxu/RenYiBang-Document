package com.example.renyibang.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

  @OneToMany(mappedBy = "serviceChat")
  @OrderBy("createdAt DESC")
  private List<ServiceChatMessage> messages;
}
