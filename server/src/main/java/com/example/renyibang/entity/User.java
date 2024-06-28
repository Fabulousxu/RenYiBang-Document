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
  @Column(name = "user_id")
  private long userId; // 用户id

  @Column(name = "type")
  private byte type = 0; // 用户类型(0:普通用户,1:客服,2:管理员)
  @Column(name = "nickname")
  private String nickname; // 用户昵称
  @Column(name = "avatar")
  private String avatar; // 用户头像
  @Column(name = "intro")
  private String intro; // 用户介绍
  @Column(name = "rating")
  private byte rating = 50; // 用户评分(存储10倍评分,范围0~100)
  @Column(name = "balance")
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

  @OneToMany(mappedBy = "owner")
  private List<TaskOrder> ownedTaskOrders; // 所有者任务订单列表

  @OneToMany(mappedBy = "accessor")
  private List<TaskOrder> accessedTaskOrders; // 接取者任务订单列表

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
