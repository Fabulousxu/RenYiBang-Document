package com.example.renyibang.entity;

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

  // equals and hashCode methods
  // ...
}
