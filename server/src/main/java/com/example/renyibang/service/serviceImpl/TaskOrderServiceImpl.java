package com.example.renyibang.service.serviceImpl;

import com.example.renyibang.dao.OrderDao;
import com.example.renyibang.dao.TaskDao;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.entity.User;
import com.example.renyibang.enums.OrderStatus;
import com.example.renyibang.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskOrderServiceImpl implements OrderService<TaskOrder, Task>{
  @Autowired private OrderDao<TaskOrder, Task> taskOrderDao;
  @Autowired private TaskDao taskDao;
  @Autowired private UserDao userDao;

  @Override
  public List<TaskOrder> findByOwnerId(long ownerId) {
    User owner = userDao.findById(ownerId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + ownerId));
    return taskOrderDao.findByOwner(owner);
  }

  @Override
  public List<TaskOrder> findByAccessorId(long accessorId) {
    User accessor = userDao.findById(accessorId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + accessorId));
    return taskOrderDao.findByAccessor(accessor);
  }

  @Override
  public List<TaskOrder> findByItemId(long taskId) {
    Task task = taskDao.findById(taskId);
    if(task == null) {
      throw new EntityNotFoundException("Task not found with id " + taskId);
    }
    return taskOrderDao.findByItem(task);
  }

  @Override
  public List<TaskOrder> findByStatus(OrderStatus status) {
    return taskOrderDao.findByStatus(status);
  }

  @Override
  public TaskOrder findById(long taskOrderId) {
    return taskOrderDao.findById(taskOrderId);
  }

  @Override
  public List<TaskOrder> findAllOrders() {
    return taskOrderDao.findAllOrders();
  }

  @Override
  public long createOrder(long taskId, long ownerId, long accessorId, long cost) {
    // Fetch the Task entity
    Task task = taskDao.findById(taskId);
    if(task == null) {
      throw new EntityNotFoundException("Task not found with id " + taskId);
    }

    // Fetch the Owner (User) entity
    User owner = userDao.findById(ownerId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + ownerId));

    // Fetch the Accessor (User) entity
    User accessor = userDao.findById(accessorId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + accessorId));


    // Create a new TaskOrder
    TaskOrder taskOrder = new TaskOrder();
    taskOrder.setItem(task);
    taskOrder.setOwner(owner);
    taskOrder.setAccessor(accessor);
    taskOrder.setCost(cost);
    taskOrder.setStatus(OrderStatus.UNPAID);

    // Save the TaskOrder and return its ID
    return taskOrderDao.save(taskOrder).getOrderId();
  }


  @Override
  public boolean markOrderStatus(long taskOrderId, OrderStatus status) {
    TaskOrder taskOrder = taskOrderDao.findById(taskOrderId);
    if (taskOrder == null) {
      return false;
    }
    taskOrder.setStatus(status);
    taskOrderDao.save(taskOrder);
    return true;
  }

  @Override
  public boolean checkOrderExist(long taskOrderId) {
    return taskOrderDao.existsById(taskOrderId);
  }

  @Override
  public boolean checkOrderStatus(long taskOrderId, OrderStatus status) {
    return taskOrderDao.findById(taskOrderId).getStatus() == status;
  }

  @Override
  public void payOrder(TaskOrder taskOrder) {
    // 获得任务发布者
    User owner = taskOrder.getOwner();
    this.modifyUserBalance(owner, -taskOrder.getCost());

    // 修改订单状态
    taskOrder.setStatus(OrderStatus.IN_PROGRESS);
    taskOrderDao.save(taskOrder);
  }

  @Override
  public void modifyUserBalance(User user, long amount) {
    if(user == null) {
      throw new EntityNotFoundException("User not found");
    }
    user.setBalance(user.getBalance() + amount);
    userDao.save(user);
  }
}
