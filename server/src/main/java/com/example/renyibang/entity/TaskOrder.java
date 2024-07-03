package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "task_order", schema = "renyibang")
@NoArgsConstructor
public class TaskOrder extends Order<Task> {

  @ManyToOne
  @JoinColumn(name = "task_id", referencedColumnName = "task_id", foreignKey = @ForeignKey(name = "FK_TASK"))
  private Task item;

  @Override
  public Task getItem() {
    return item;
  }

  @Override
  public void setItem(Task item) {
    this.item = item;
  }

  public JSONObject toJSON() {
    JSONObject json = super.toJSON();
    json.put("time", item.getCreatedAt());
    json.put("name", item.getTitle());
    return json;
  }

  // equals and hashCode methods
  // ...
}
