package com.example.renyibang.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "task_access")
@Getter
@Setter
@NoArgsConstructor
public class TaskAccess {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long taskAccessId; // 任务接取候选id

  @ManyToOne
  @JoinColumn(name = "task_id")
  private Task task; // 任务

  @ManyToOne
  @JoinColumn(name = "accessor_id")
  private User accessor; // 任务接取者

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt; // 任务接取时间

  @Column(name = "valid")
  private boolean valid = true; //接取候选是否有效
}
