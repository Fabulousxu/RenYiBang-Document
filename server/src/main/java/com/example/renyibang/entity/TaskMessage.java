package com.example.renyibang.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "task_message")
@Getter
@Setter
@NoArgsConstructor
public class TaskMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long taskMessageId; // 任务留言id

  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task; // 任务

  @ManyToOne
  @JoinColumn(name = "messager_id")
  private User messager; // 留言者

  private String content; // 留言内容

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private java.util.Date createdAt; // 留言时间

  @ManyToMany
  @JoinTable(
      name = "task_message_like",
      joinColumns = @JoinColumn(name = "task_message_id"),
      inverseJoinColumns = @JoinColumn(name = "liker_id"))
  private Set<User> likers; // 留言点赞表
}
