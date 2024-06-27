package com.example.renyibang.dao;

import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.enums.TaskStatus;

import javax.net.ssl.SSLSession;
import java.util.List;

public interface TaskOrderDao {
  List<TaskOrder> findByOwnerId(long ownerId);

  List<TaskOrder> findByAccessorId(long accessorId);

  List<TaskOrder> findByTaskId(long taskId);

  List<TaskOrder> findByStatus(TaskStatus status);

  TaskOrder findTaskOrderById(long taskOrderId);

  List<TaskOrder> findAllTaskOrders();

  TaskOrder save(TaskOrder taskOrder);

  boolean existsById(long taskOrderId);
}
