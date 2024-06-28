package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.renyibang.util.DateTimeUtil;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "task")
@Getter
@Setter
@NoArgsConstructor
public class Task {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_id")
  private long taskId; // 任务id

  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner; // 任务发布者

  @Column(name = "title")
  private String title; // 任务标题
  @Column(name = "images")
  private String images; // 任务图片
  @Column(name = "description")
  private String description; // 任务描述
  @Column(name = "price")
  private long price = 0; // 任务价格(存储100倍价格)
  @Column(name = "max_access")
  private int maxAccess = 0; // 任务最大接取数
  @Column(name = "rating")
  private byte rating = 50; // 任务评分(存储10倍评分,范围0~100)

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt; // 任务创建时间

  @OneToMany(mappedBy = "task")
  @OrderBy("createdAt DESC")
  @JsonIgnore
  private List<TaskComment> comments; // 任务评论列表

  @OneToMany(mappedBy = "task")
  @OrderBy("createdAt DESC")
  @JsonIgnore
  private List<TaskMessage> messages; // 任务留言列表

  @OneToMany(mappedBy = "task")
  @OrderBy("createdAt DESC")
  @JsonIgnore
  private List<TaskAccess> accesses; // 任务接取候选列表

  @OneToMany(mappedBy = "task")
  @JsonIgnore
  private List<TaskOrder> taskOrders; // 任务订单列表

  public static List<String> splitImages(String images) {
    // 使用空格分割字符串，并将结果转换为List<String>
    return Arrays.asList(images.split("\\s+"));
  }

  public JSONObject toJSON() {
    JSONObject result = new JSONObject();
    result.put("taskId", taskId);
    result.put("title", title);
    List<String> imageList = splitImages(images);
    result.put("images", imageList);
    result.put("cover", imageList.getFirst());
    result.put("description", description);
    result.put("price", price);
    result.put("maxAccess", maxAccess);
    result.put("rating", rating);
    result.put("createdAt", DateTimeUtil.formatDateTime(createdAt));

    result.put("owner", owner.toJSON());

    return result;
  }
}
