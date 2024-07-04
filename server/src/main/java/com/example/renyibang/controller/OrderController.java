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
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
  @GetMapping("/task/{id}")
  public JSONObject getTaskOrder(@PathVariable Long id) {
    // TODO: token, 获取当前用户id
    // 身份校验
    Order order = taskOrderService.findById(id);
    OrderDto orderDto = new OrderDto(order);
    return ResponseUtil.success(orderDto.toJSON());
  }

  @GetMapping("/service/{id}")
  public JSONObject getServiceOrder(@PathVariable Long id) {
    // TODO: token, 获取当前用户id
    // 身份校验
    Order order = serviceOrderService.findById(id);
    OrderDto orderDto = new OrderDto(order);
    return ResponseUtil.success(orderDto.toJSON());
  }


  @PutMapping("/task/status")
  public JSONObject ChangeTaskOrderStatus(@RequestParam Long id, @RequestParam int status) {
    // TODO: token, 获取当前用户id
    // 测试用: userId = 1
    int userId = 1;

    Pair<Boolean, String> result = taskOrderService.markOrderStatus(id, OrderStatus.values()[status]);
    if (result.a) {
      return ResponseUtil.success(result.b);
    } else {
      return ResponseUtil.error(result.b);
    }
  }

  @PutMapping("/service/status")
  public JSONObject ChangeServiceOrderStatus(@RequestParam Long id, @RequestParam int status) {
    Pair<Boolean, String> result = serviceOrderService.markOrderStatus(id, OrderStatus.values()[status]);
    if (result.a) {
      return ResponseUtil.success(result.b);
    } else {
      return ResponseUtil.error(result.b);
    }
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
