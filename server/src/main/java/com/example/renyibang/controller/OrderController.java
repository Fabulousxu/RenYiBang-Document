package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.dto.OrderDto;
import com.example.renyibang.entity.Order;
import com.example.renyibang.entity.Service;
import com.example.renyibang.entity.ServiceOrder;
import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.enums.OrderStatus;
import com.example.renyibang.service.OrderService;
import com.example.renyibang.util.ResponseUtil;
import java.util.ArrayList;
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
  @Autowired
  private OrderService<ServiceOrder, Service> serviceOrderService;

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

  // 获取指定id的order信息
  // /api/order/{id}
  @GetMapping("/{id}")
  public JSONObject getOrder(@PathVariable Long id) {
    // TODO: token, 获取当前用户id
    // 身份校验
    Order order = taskOrderService.findById(id);
    OrderDto orderDto = new OrderDto(order);
    return ResponseUtil.success(orderDto.toJSON());
  }

  // /task/owner/:ownerId
  @GetMapping("/task/initiator/{ownerId}")
  public JSONObject getTaskOrderByOwner(@PathVariable Long ownerId) {
    // TODO：取消路径参数，使用token获取用户id
    List<TaskOrder> taskOrders = taskOrderService.findByOwnerId(ownerId);
    return ResponseUtil.success(toJSON(taskOrders));
  }

  @GetMapping("/task/recipient/{accessorId}")
  public JSONObject getTaskOrderByAccessor(@PathVariable Long accessorId) {
    // TODO：取消路径参数，使用token获取用户id
    List<TaskOrder> taskOrders = taskOrderService.findByAccessorId(accessorId);
    return ResponseUtil.success(toJSON(taskOrders));
  }
  @GetMapping("/service/initiator/{ownerId}")
  public JSONObject getServiceOrderByOwner(@PathVariable Long ownerId) {
    // TODO：取消路径参数，使用token获取用户id
    List<ServiceOrder> serviceOrders = serviceOrderService.findByOwnerId(ownerId);
    return ResponseUtil.success(toJSON(serviceOrders));
  }
  @GetMapping("/service/recipient/{accessorId}")
  public JSONObject getServiceOrderByAccessor(@PathVariable Long accessorId) {
    // TODO：取消路径参数，使用token获取用户id
    List<ServiceOrder> serviceOrders = serviceOrderService.findByAccessorId(accessorId);
    return ResponseUtil.success(toJSON(serviceOrders));
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
    return ResponseUtil.success("支付成功");
  }

  @PostMapping("/service/pay/{orderId}")
  public JSONObject payServiceOrder(@PathVariable Long orderId) {
    return ResponseUtil.success("支付成功");
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
    return ResponseUtil.success("修改订单状态成功");
  }

  @PostMapping("/service/status")
  public JSONObject completeServiceOrder(@RequestParam Long orderId, @RequestParam OrderStatus status) {
    // TODO: token, 获取当前用户id
    // 测试用: userId = 1
    int userId = 1;

    // 已知orderId, 获取order
    ServiceOrder serviceOrder = serviceOrderService.findById(orderId);
    // 校验用户是否为任务接收者
    if(serviceOrder.getAccessor().getUserId() != userId) {
      return ResponseUtil.error("该用户不是任务接收者");
    }
    // 修改订单状态
    serviceOrder.setStatus(status);
    return ResponseUtil.success("修改订单状态成功");
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
    // TODO: token, 获取当前用户id
    // 测试用: userId = 1
    int userId = 1;

    // 已知orderId, 获取order
    ServiceOrder serviceOrder = serviceOrderService.findById(orderId);
    // 校验用户是否为任务发起者
    if (serviceOrder.getOwner().getUserId() != userId) {
      return ResponseUtil.error("该用户不是任务发起者");
    }
    // 校验任务状态是否为已完成
    if (serviceOrder.getStatus() != OrderStatus.COMPLETED) {
      return ResponseUtil.error("任务状态错误：未已完成");
    }
    // 修改订单状态
    serviceOrder.setStatus(OrderStatus.CONFIRMED);

    // 将帐号余额增加
    taskOrderService.modifyUserBalance(serviceOrder.getAccessor(), serviceOrder.getCost());
    return ResponseUtil.success("订单确认完成");
  }

  // 非接口
  // 传入List<Order>，对于每一个Order，调用toJSON()方法，返回List<JSONObject>
  // 使用模板
  private List<JSONObject> toJSON(List<? extends Order> orders) {
    List<JSONObject> jsonObjects = new ArrayList<>();
    for (Order order : orders) {
      jsonObjects.add(order.toJSON());
    }
    return jsonObjects;
  }
}
