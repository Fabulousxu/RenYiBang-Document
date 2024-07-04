package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

  private int unread;
  private String lastMessage;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime lastTime;

  @ManyToOne
  @JoinColumn(name = "last_chatter_id")
  private User lastChatter;

  @OneToMany(mappedBy = "taskChat")
  @OrderBy("createdAt DESC")
  private List<TaskChatMessage> messages;
}
