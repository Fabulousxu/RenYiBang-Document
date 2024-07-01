package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.TaskOrderDao;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.entity.User;
import com.example.renyibang.entity.Task;
import com.example.renyibang.enums.TaskStatus;
import com.example.renyibang.repository.TaskOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskOrderDaoImpl implements TaskOrderDao {
  @Autowired private TaskOrderRepository taskOrderRepository;

  @Override
  public List<TaskOrder> findByOwner(User owner) {
    return taskOrderRepository.findByOwner(owner);
  }

  @Override
  public List<TaskOrder> findByAccessor(User accessor) {
    return taskOrderRepository.findByAccessor(accessor);
  }

  @Override
  public List<TaskOrder> findByTask(Task task) {
    return taskOrderRepository.findByTask(task);
  }

  @Override
  public List<TaskOrder> findByStatus(TaskStatus status) {
    return taskOrderRepository.findByStatus(status);
  }

  @Override
  public TaskOrder findById(long taskOrderId) {
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
