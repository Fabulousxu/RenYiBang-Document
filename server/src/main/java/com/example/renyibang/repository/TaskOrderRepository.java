package com.example.renyibang.repository;

import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.entity.User;
import com.example.renyibang.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskOrderRepository extends JpaRepository<TaskOrder, Long> {
  List<TaskOrder> findByOwner(User owner);

  List<TaskOrder> findByAccessor(User accessor);

  List<TaskOrder> findByTask(Task task);

  List<TaskOrder> findByStatus(TaskStatus status);
}
