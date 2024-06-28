package com.example.renyibang.dao;

import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.entity.User;
import com.example.renyibang.enums.TaskStatus;

import javax.net.ssl.SSLSession;
import java.util.List;

public interface TaskOrderDao {
  List<TaskOrder> findByOwner(User owner);

  List<TaskOrder> findByAccessor(User accessor);

  List<TaskOrder> findByTask(Task task);

  List<TaskOrder> findByStatus(TaskStatus status);

  TaskOrder findTaskOrderById(long taskOrderId);

  List<TaskOrder> findAllTaskOrders();

  TaskOrder save(TaskOrder taskOrder);

  boolean existsById(long taskOrderId);
}
