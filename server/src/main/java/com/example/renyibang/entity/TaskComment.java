package com.example.renyibang.entity;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "task_comment")
@Getter
@Setter
@NoArgsConstructor
public class TaskComment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long taskCommentId; // 评论id

  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task; // 任务

  @ManyToOne
  @JoinColumn(name = "commenter_id")
  private User commenter; // 评论者

  private String content; // 评论内容
  private byte rating = 50; // 评论评分(存储10倍评分,范围0~100)

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private java.util.Date createdAt; // 评论时间

  @ManyToMany
  @JoinTable(
      name = "task_comment_like",
      joinColumns = @JoinColumn(name = "task_comment_id"),
      inverseJoinColumns = @JoinColumn(name = "liker_id"))
  private Set<User> likers; // 评论点赞表
}
