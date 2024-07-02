package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.util.DateTimeUtil;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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
  private LocalDateTime createdAt; // 留言时间

  @ManyToMany
  @JoinTable(
      name = "task_message_like",
      joinColumns = @JoinColumn(name = "task_message_id"),
      inverseJoinColumns = @JoinColumn(name = "liker_id"))
  private Set<User> likers; // 留言点赞表

  @Column(name = "liked_number")
  private long likedNumber = 0;

  public JSONObject toJSON()
  {
    JSONObject result = new JSONObject();
    result.put("taskMessageId", taskMessageId);
    result.put("taskId", task.getTaskId());
    result.put("messagerId", messager.getUserId());
    result.put("content", content);
    result.put("createdAt", DateTimeUtil.formatDateTime(createdAt));
    result.put("likedNumber", likedNumber);

    result.put("messager", messager.toJSON());

    return result;
  }
}
