package com.example.renyibang.entity;

import com.example.renyibang.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "task_order", schema = "renyibang")
@NoArgsConstructor
public class TaskOrder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "task_order_id")
  private long taskOrderId;

  @ManyToOne
  @JoinColumn(name = "task_id", referencedColumnName = "task_id", foreignKey = @ForeignKey(name = "FK_TASK"))
  private Task task;

  @ManyToOne
  @JoinColumn(name = "owner_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_OWNER"))
  private User owner;

  @ManyToOne
  @JoinColumn(name = "accessor_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_ACCESSOR"))
  private User accessor;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private TaskStatus status;

  @Column(name = "cost")
  private long cost;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TaskOrder taskOrder = (TaskOrder) o;
    return taskOrderId == taskOrder.taskOrderId
        && cost == taskOrder.cost
        && Objects.equals(task, taskOrder.task)
        && Objects.equals(owner, taskOrder.owner)
        && Objects.equals(accessor, taskOrder.accessor)
        && status == taskOrder.status;
  }

  @Override
  public int hashCode() {
    return Objects.hash(taskOrderId, task, owner, accessor, status, cost);
  }
}
