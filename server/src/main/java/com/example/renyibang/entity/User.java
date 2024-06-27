package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long userId; // 用户id

  private byte type = 0; // 用户类型(0:普通用户,1:客服,2:管理员)
  private String nickname; // 用户昵称
  private String avatar; // 用户头像
  private String intro; // 用户介绍
  private byte rating = 50; // 用户评分(存储10倍评分,范围0~100)
  private long balance = 0; // 用户余额(存储100倍余额)

  @ManyToMany
  @JoinTable(
      name = "follow",
      joinColumns = @JoinColumn(name = "follower_id"),
      inverseJoinColumns = @JoinColumn(name = "followee_id"))
  private Set<User> following; // 关注列表

  @ManyToMany
  @JoinTable(
      name = "follow",
      joinColumns = @JoinColumn(name = "followee_id"),
      inverseJoinColumns = @JoinColumn(name = "follower_id"))
  private Set<User> follower; // 粉丝列表

  @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
  @OrderBy("createdAt DESC")
  private List<Task> tasks; // 发布任务列表

  @OneToMany(mappedBy = "collector")
  @OrderBy("createdAt DESC")
  private List<TaskCollect> collectedTasks; // 收藏任务列表

  @OneToMany(mappedBy = "accessor")
  @OrderBy("createdAt DESC")
  private List<TaskAccess> accessedTasks; // 接取任务列表

  public JSONObject toJSON() {
    JSONObject result = new JSONObject();
    result.put("userId", userId);
    result.put("type", getType());
    result.put("nickname", getNickname());
    result.put("avatar", getAvatar());
    result.put("intro", getIntro());
    result.put("rating", getRating());
    result.put("balance", getBalance());

    return result;
  }
}
