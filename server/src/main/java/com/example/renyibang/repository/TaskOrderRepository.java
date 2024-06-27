package com.example.renyibang.repository;

import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskOrderRepository extends JpaRepository<TaskOrder, Long> {
  List<TaskOrder> findByOwnerId(long ownerId);

  List<TaskOrder> findByAccessorId(long accessorId);

  List<TaskOrder> findByTaskId(long taskId);

  List<TaskOrder> findByStatus(TaskStatus status);
}
