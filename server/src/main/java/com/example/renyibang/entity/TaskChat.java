package com.example.renyibang.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "task_chat")
@Getter
@Setter
@NoArgsConstructor
public class TaskChat {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long taskChatId;

  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task;

  @ManyToOne
  @JoinColumn(name = "chatter_id")
  private User chatter;

  @OneToMany(mappedBy = "taskChat")
  @OrderBy("createdAt DESC")
  private List<TaskChatMessage> messages;
}
