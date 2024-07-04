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
import org.antlr.v4.runtime.misc.Pair;
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
    return taskOrderDao.findByOwnerAndType(owner, (byte) 0);
  }

  @Override
  public List<TaskOrder> findByAccessorId(long accessorId) {
    User accessor = userDao.findById(accessorId)
            .orElseThrow(() -> new EntityNotFoundException("User not found with id " + accessorId));
    return taskOrderDao.findByAccessorAndType(accessor, (byte) 0);
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
    return taskOrderDao.findByStatusAndType(status, (byte) 0);
  }

  @Override
  public TaskOrder findById(long taskOrderId) {
    return taskOrderDao.findById(taskOrderId);
  }

  @Override
  public List<TaskOrder> findAllOrders() {
    return taskOrderDao.findByType((byte) 0);
//    return taskOrderDao.findAllOrders();
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
  public Pair<Boolean, String> markOrderStatus(long taskOrderId, OrderStatus status) {
    TaskOrder taskOrder = taskOrderDao.findById(taskOrderId);
    if (taskOrder == null) {
      return new Pair<>(false, "订单不存在");
    }

    OrderStatus currentStatus = taskOrder.getStatus();
    if (currentStatus == OrderStatus.UNPAID && status == OrderStatus.IN_PROGRESS) {
      this.payOrder(taskOrder);
      return new Pair<>(true, "订单支付成功");
    } else if (currentStatus == OrderStatus.IN_PROGRESS && status == OrderStatus.COMPLETED) {
      this.completeOrder(taskOrder);
      return new Pair<>(true, "订单完成成功");
    } else if (currentStatus == OrderStatus.COMPLETED && status == OrderStatus.CONFIRMED) {
      this.confirmOrder(taskOrder);
      return new Pair<>(true, "订单确认成功");
    } else if(status == OrderStatus.CANCELLED && currentStatus != OrderStatus.CONFIRMED && currentStatus != OrderStatus.CANCELLED) {
      this.cancelOrder(taskOrder);
      return new Pair<>(true, "订单取消成功");
    } else {
      return new Pair<>(false, "订单状态不合法");
    }
  }

  @Override
  public boolean setOrderStatusForce(long orderId, OrderStatus status) {
    TaskOrder taskOrder = taskOrderDao.findById(orderId);
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
  public void completeOrder(TaskOrder order) {
    order.setStatus(OrderStatus.COMPLETED);
    taskOrderDao.save(order);
  }

  @Override
  public void confirmOrder(TaskOrder order) {
    order.setStatus(OrderStatus.CONFIRMED);
    // 将帐号余额增加
    this.modifyUserBalance(order.getAccessor(), order.getCost());
    taskOrderDao.save(order);
  }

  @Override
  public void cancelOrder(TaskOrder order) {
    order.setStatus(OrderStatus.CANCELLED);
    // 将帐号余额增加
    this.modifyUserBalance(order.getOwner(), order.getCost());
    taskOrderDao.save(order);
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

// 支付订单
// ？怎么实现？不同的支付api?
// 设计模式？
//  @PostMapping("/task/pay/{orderId}")
//  public JSONObject payTaskOrder(@PathVariable Long orderId) {
//    // TODO: token, 获取当前用户id
//    // TODO：支付api
//    int userId = 1;
//    // 校验用户是否为任务发布者
//    TaskOrder taskOrder = taskOrderService.findById(orderId);
//    if (taskOrder.getOwner().getUserId() != userId) {
//      return ResponseUtil.error("该用户不是任务发布者");
//    }
//
//    // 支付订单
//    taskOrderService.payOrder(taskOrder);
//    return ResponseUtil.success("支付成功");
//  }
//
//  @PostMapping("/service/pay/{orderId}")
//  public JSONObject paytaskOrder(@PathVariable Long orderId) {
//    return ResponseUtil.success("支付成功");
//  }
//
//  // 任务完成
//  @PostMapping("/task/complete")
//  public JSONObject completeTaskOrder(@RequestParam Long orderId) {
//    // TODO: token, 获取当前用户id
//    // 测试用: userId = 1
//    int userId = 1;
//
//    // 已知orderId, 获取order
//    TaskOrder taskOrder = taskOrderService.findById(orderId);
//    // 校验用户是否为任务接收者
//    if(taskOrder.getAccessor().getUserId() != userId) {
//      return ResponseUtil.error("该用户不是任务接收者");
//    }
//
//    // 检查任务状态是否为进行中
//    if (taskOrder.getStatus() != OrderStatus.IN_PROGRESS) {
//      return ResponseUtil.error("任务状态错误：未进行中");
//    }
//    // 修改订单状态
//    taskOrder.setStatus(OrderStatus.COMPLETED);
//    return ResponseUtil.success("修改订单状态成功");
//  }
//
//  @PostMapping("/service/complete")
//  public JSONObject completetaskOrder(@RequestParam Long orderId) {
//    // TODO: token, 获取当前用户id
//    // 测试用: userId = 1
//    int userId = 1;
//
//    // 已知orderId, 获取order
//    taskOrder taskOrder = taskOrderService.findById(orderId);
//    // 校验用户是否为任务接收者
//    if(taskOrder.getAccessor().getUserId() != userId) {
//      return ResponseUtil.error("该用户不是任务接收者");
//    }
//
//    // 检查任务状态是否为进行中
//    if (taskOrder.getStatus() != OrderStatus.IN_PROGRESS) {
//      return ResponseUtil.error("任务状态错误：未进行中");
//    }
//    // 修改订单状态
//    taskOrder.setStatus(OrderStatus.COMPLETED);
//    return ResponseUtil.success("修改订单状态成功");
//  }
//
//  // 确认订单完成
//  @PostMapping("/task/confirm/{orderId}")
//  public JSONObject confirmTaskOrder(@PathVariable Long orderId) {
//    // TODO: token, 获取当前用户id
//    // 测试用: userId = 1
//    int userId = 3;
//
//    // 已知orderId, 获取order
//    TaskOrder taskOrder = taskOrderService.findById(orderId);
//    // 校验用户是否为任务发起者
//    if (taskOrder.getOwner().getUserId() != userId) {
//      return ResponseUtil.error("该用户不是任务发起者");
//    }
//    // 校验任务状态是否为已完成
//    if (taskOrder.getStatus() != OrderStatus.COMPLETED) {
//      return ResponseUtil.error("任务状态错误：未已完成");
//    }
//    // 修改订单状态
//    taskOrder.setStatus(OrderStatus.CONFIRMED);
//
//    // 将帐号余额增加
//    taskOrderService.modifyUserBalance(taskOrder.getAccessor(), taskOrder.getCost());
//    return ResponseUtil.success("订单确认完成");
//  }
//
//  @PostMapping("/service/confirm/{orderId}")
//  public JSONObject confirmtaskOrder(@PathVariable Long orderId) {
//    // TODO: token, 获取当前用户id
//    // 测试用: userId = 1
//    int userId = 1;
//
//    // 已知orderId, 获取order
//    taskOrder taskOrder = taskOrderService.findById(orderId);
//    // 校验用户是否为任务发起者
//    if (taskOrder.getOwner().getUserId() != userId) {
//      return ResponseUtil.error("该用户不是任务发起者");
//    }
//    // 校验任务状态是否为已完成
//    if (taskOrder.getStatus() != OrderStatus.COMPLETED) {
//      return ResponseUtil.error("任务状态错误：未已完成");
//    }
//    // 修改订单状态
//    taskOrder.setStatus(OrderStatus.CONFIRMED);
//
//    // 将帐号余额增加
//    taskOrderService.modifyUserBalance(taskOrder.getAccessor(), taskOrder.getCost());
//    return ResponseUtil.success("订单确认完成");
//  }