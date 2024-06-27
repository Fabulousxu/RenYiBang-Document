package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.TaskOrderDao;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.enums.TaskStatus;
import com.example.renyibang.repository.TaskOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskOrderDaoImpl implements TaskOrderDao {
  @Autowired private TaskOrderRepository taskOrderRepository;

  @Override
  public List<TaskOrder> findByOwnerId(long ownerId) {
    return taskOrderRepository.findByOwnerId(ownerId);
  }

  @Override
  public List<TaskOrder> findByAccessorId(long accessorId) {
    return taskOrderRepository.findByAccessorId(accessorId);
  }

  @Override
  public List<TaskOrder> findByTaskId(long taskId) {
    return taskOrderRepository.findByTaskId(taskId);
  }

  @Override
  public List<TaskOrder> findByStatus(TaskStatus status) {
    return taskOrderRepository.findByStatus(status);
  }

  @Override
  public TaskOrder findTaskOrderById(long taskOrderId) {
    return taskOrderRepository.findById(taskOrderId).orElse(null);
  }

  @Override
  public List<TaskOrder> findAllTaskOrders() {
    return taskOrderRepository.findAll();
  }

  // save
  @Override
  public TaskOrder save(TaskOrder taskOrder) {
    return taskOrderRepository.save(taskOrder);
  }

  // existsById
  @Override
  public boolean existsById(long taskOrderId) {
    return taskOrderRepository.existsById(taskOrderId);
  }
}
