package com.example.renyibang.service;

import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.enums.TaskStatus;

import java.util.List;

public interface TaskOrderService {
  List<TaskOrder> findByOwnerId(long ownerId);

  List<TaskOrder> findByAccessorId(long accessorId);

  List<TaskOrder> findByTaskId(long taskId);

  List<TaskOrder> findByStatus(TaskStatus status);

  // 根据订单ID查找订单
  TaskOrder findById(long taskOrderId);

  // 返回所有订单
  List<TaskOrder> findAllTaskOrders();

  // 生成初始订单
  long createTaskOrder(long taskId, long ownerId, long accessorId, long cost);

  // 标记订单状态
  boolean markTaskOrderStatus(long taskOrderId, TaskStatus status);

  // 校验订单是否存在
  boolean checkTaskOrderExist(long taskOrderId);

  // 校验订单状态是否为指定状态
  boolean checkTaskOrderStatus(long taskOrderId, TaskStatus status);
}
