package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.enums.TaskStatus;
import com.example.renyibang.service.TaskOrderService;
import com.example.renyibang.util.ResponseUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
  @Autowired
  private TaskOrderService taskOrderService;

  // /task/owner/:ownerId
  @GetMapping("/task/initiator/{ownerId}")
  public JSONObject getTaskOrderByOwner(@PathVariable Long ownerId) {
    List<TaskOrder> taskOrders = taskOrderService.findByOwnerId(ownerId);
    return ResponseUtil.success(taskOrders);
  }

  @GetMapping("/task/recipient/{accessorId}")
  public JSONObject getTaskOrderByAccessor(@PathVariable Long accessorId) {
    List<TaskOrder> taskOrders = taskOrderService.findByAccessorId(accessorId);
    return ResponseUtil.success(taskOrders);
  }
  @GetMapping("/service/initiator")
  public JSONObject getServiceOrderByOwner() {

    return ResponseUtil.success("");
  }
  @GetMapping("/service/recipient")
  public JSONObject getServiceOrderByAccessor() {

    return ResponseUtil.success("");
  }

  // 支付订单
  // ？怎么实现？不同的支付api?
  // 设计模式？
  @PostMapping("/task/pay/{orderId}")
  public JSONObject payTaskOrder(@PathVariable Long orderId) {
    return ResponseUtil.success("");
  }

  @PostMapping("/service/pay/{orderId}")
  public JSONObject payServiceOrder(@PathVariable Long orderId) {
    return ResponseUtil.success("");
  }

  // 任务完成
  @PostMapping("/task/complete/{orderId}")
  public JSONObject completeTaskOrder(@PathVariable Long orderId) {
    // TODO: token, 获取当前用户id

    // 已知orderId, 获取order
    TaskOrder taskOrder = taskOrderService.findById(orderId);
    // 校验用户是否为任务接收者
    if (taskOrder.getAccessor().getUserId() != 1) {
      return ResponseUtil.error("该用户不是任务接收者");
    }
    return ResponseUtil.success("");
  }

  @PostMapping("/service/complete/{orderId}")
  public JSONObject completeServiceOrder(@PathVariable Long orderId) {
    return ResponseUtil.success("");
  }

  // 确认订单完成
  @PostMapping("/task/confirm/{orderId}")
  public JSONObject confirmTaskOrder(@PathVariable Long orderId) {
    return ResponseUtil.success("");
  }

  @PostMapping("/service/confirm/{orderId}")
  public JSONObject confirmServiceOrder(@PathVariable Long orderId) {
    return ResponseUtil.success("");
  }
}
