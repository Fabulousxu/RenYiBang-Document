package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.enums.OrderStatus;
import com.example.renyibang.service.OrderService;
import com.example.renyibang.util.ResponseUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
  @Autowired
  private OrderService<TaskOrder, Task> taskOrderService;

  // 生成订单
  // /task/create
  // Long taskId, Long ownerId, Long accessorId, Long cost
  // token中的userId
  @PostMapping("/task/create")
  public JSONObject createTaskOrder(@RequestBody JSONObject data) {
    // TODO: token, 获取当前用户id
    // 测试用: userId = 1
    int userId = 1;

    long taskId = data.getLong("taskId");
    long ownerId = data.getLong("ownerId");
    long accessorId = data.getLong("accessorId");
    long cost = data.getLong("cost");
    // 校验ownerId是否为当前用户
    if (ownerId != userId) {
      return ResponseUtil.error("ownerId不匹配");
    }

    // 创建订单
    taskOrderService.createOrder(taskId, ownerId, accessorId, cost);
    return ResponseUtil.success("订单创建成功");
  }

  // /task/owner/:ownerId
  @GetMapping("/task/initiator/{ownerId}")
  public JSONObject getTaskOrderByOwner(@PathVariable Long ownerId) {
    // TODO：取消路径参数，使用token获取用户id
    List<TaskOrder> taskOrders = taskOrderService.findByOwnerId(ownerId);
    return ResponseUtil.success(taskOrders);
  }

  @GetMapping("/task/recipient/{accessorId}")
  public JSONObject getTaskOrderByAccessor(@PathVariable Long accessorId) {
    // TODO：取消路径参数，使用token获取用户id
    List<TaskOrder> taskOrders = taskOrderService.findByAccessorId(accessorId);
    return ResponseUtil.success(taskOrders);
  }
  @GetMapping("/service/initiator")
  public JSONObject getServiceOrderByOwner() {
    // TODO：取消路径参数，使用token获取用户id
    return ResponseUtil.success("");
  }
  @GetMapping("/service/recipient")
  public JSONObject getServiceOrderByAccessor() {
    // TODO：取消路径参数，使用token获取用户id
    return ResponseUtil.success("");
  }

  // 支付订单
  // ？怎么实现？不同的支付api?
  // 设计模式？
  @PostMapping("/task/pay/{orderId}")
  public JSONObject payTaskOrder(@PathVariable Long orderId) {
    // TODO: token, 获取当前用户id
    // TODO：支付api
    int userId = 1;
    // 校验用户是否为任务发布者
    TaskOrder taskOrder = taskOrderService.findById(orderId);
    if (taskOrder.getOwner().getUserId() != userId) {
      return ResponseUtil.error("该用户不是任务发布者");
    }

    // 支付订单
    taskOrderService.payOrder(taskOrder);
    return ResponseUtil.success("");
  }

  @PostMapping("/service/pay/{orderId}")
  public JSONObject payServiceOrder(@PathVariable Long orderId) {
    return ResponseUtil.success("");
  }

  // 任务完成
  @PostMapping("/task/status")
  public JSONObject completeTaskOrder(@RequestParam Long orderId, @RequestParam OrderStatus status) {
    // TODO: token, 获取当前用户id
    // 测试用: userId = 1
    int userId = 1;

    // 已知orderId, 获取order
    TaskOrder taskOrder = taskOrderService.findById(orderId);
    // 校验用户是否为任务接收者
    if(taskOrder.getAccessor().getUserId() != userId) {
      return ResponseUtil.error("该用户不是任务接收者");
    }
//    // 校验任务状态是否为进行中
//    if (taskOrder.getStatus() != TaskStatus.IN_PROGRESS) {
//      return ResponseUtil.error("任务状态错误：未进行中");
//    }
    // 修改订单状态
//    taskOrder.setStatus(TaskStatus.COMPLETED);
    taskOrder.setStatus(status);
    return ResponseUtil.success("");
  }

  @PostMapping("/service/status")
  public JSONObject completeServiceOrder(@RequestParam Long orderId, @RequestParam OrderStatus status) {
    return ResponseUtil.success("");
  }

  // 确认订单完成
  @PostMapping("/task/confirm/{orderId}")
  public JSONObject confirmTaskOrder(@PathVariable Long orderId) {
    // TODO: token, 获取当前用户id
    // 测试用: userId = 1
    int userId = 3;

    // 已知orderId, 获取order
    TaskOrder taskOrder = taskOrderService.findById(orderId);
    // 校验用户是否为任务发起者
    if (taskOrder.getOwner().getUserId() != userId) {
      return ResponseUtil.error("该用户不是任务发起者");
    }
    // 校验任务状态是否为已完成
    if (taskOrder.getStatus() != OrderStatus.COMPLETED) {
      return ResponseUtil.error("任务状态错误：未已完成");
    }
    // 修改订单状态
    taskOrder.setStatus(OrderStatus.CONFIRMED);

    // 将帐号余额增加
    taskOrderService.modifyUserBalance(taskOrder.getAccessor(), taskOrder.getCost());
    return ResponseUtil.success("订单确认完成");
  }

  @PostMapping("/service/confirm/{orderId}")
  public JSONObject confirmServiceOrder(@PathVariable Long orderId) {
    return ResponseUtil.success("");
  }
}
