package com.example.renyibang.service.serviceImpl;

import com.example.renyibang.dao.TaskDao;
import com.example.renyibang.dao.TaskOrderDao;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.entity.User;
import com.example.renyibang.enums.TaskStatus;
import com.example.renyibang.service.TaskOrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskOrderServiceImpl implements TaskOrderService {
  @Autowired private TaskOrderDao taskOrderDao;
  @Autowired private TaskDao taskDao;
  @Autowired private UserDao userDao;

  @Override
  public List<TaskOrder> findByOwnerId(long ownerId) {
    User owner = userDao.findById(ownerId);
    if(owner == null) {
      throw new EntityNotFoundException("User not found with id " + ownerId);
    }
    return taskOrderDao.findByOwner(owner);
  }

  @Override
  public List<TaskOrder> findByAccessorId(long accessorId) {
    User accessor = userDao.findById(accessorId);
    if(accessor == null) {
      throw new EntityNotFoundException("User not found with id " + accessorId);
    }
    return taskOrderDao.findByAccessor(accessor);
  }

  @Override
  public List<TaskOrder> findByTaskId(long taskId) {
    Task task = taskDao.findById(taskId);
    if(task == null) {
      throw new EntityNotFoundException("Task not found with id " + taskId);
    }
    return taskOrderDao.findByTask(task);
  }

  @Override
  public List<TaskOrder> findByStatus(TaskStatus status) {
    return taskOrderDao.findByStatus(status);
  }

  @Override
  public TaskOrder findById(long taskOrderId) {
    return taskOrderDao.findById(taskOrderId);
  }

  @Override
  public List<TaskOrder> findAllTaskOrders() {
    return taskOrderDao.findAllTaskOrders();
  }

  @Override
  public long createTaskOrder(long taskId, long ownerId, long accessorId, long cost) {
    // Fetch the Task entity
    Task task = taskDao.findById(taskId);
    if(task == null) {
      throw new EntityNotFoundException("Task not found with id " + taskId);
    }

    // Fetch the Owner (User) entity
    User owner = userDao.findById(ownerId);
    if(owner == null) {
      throw new EntityNotFoundException("User not found with id " + ownerId);
    }

    // Fetch the Accessor (User) entity
    User accessor = userDao.findById(accessorId);
    if(accessor == null) {
      throw new EntityNotFoundException("User not found with id " + accessorId);
    }

    // Create a new TaskOrder
    TaskOrder taskOrder = new TaskOrder();
    taskOrder.setTask(task);
    taskOrder.setOwner(owner);
    taskOrder.setAccessor(accessor);
    taskOrder.setCost(cost);
    taskOrder.setStatus(TaskStatus.UNPAID);

    // Save the TaskOrder and return its ID
    return taskOrderDao.save(taskOrder).getTaskOrderId();
  }


  @Override
  public boolean markTaskOrderStatus(long taskOrderId, TaskStatus status) {
    TaskOrder taskOrder = taskOrderDao.findById(taskOrderId);
    if (taskOrder == null) {
      return false;
    }
    taskOrder.setStatus(status);
    taskOrderDao.save(taskOrder);
    return true;
  }

  @Override
  public boolean checkTaskOrderExist(long taskOrderId) {
    return taskOrderDao.existsById(taskOrderId);
  }

  @Override
  public boolean checkTaskOrderStatus(long taskOrderId, TaskStatus status) {
    return taskOrderDao.findById(taskOrderId).getStatus() == status;
  }
}
