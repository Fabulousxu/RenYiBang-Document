package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.service.TaskOrderService;
import com.example.renyibang.util.ResponseUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
  @Autowired
  private TaskOrderService taskOrderService;

  // /task/owner/:ownerId
  @GetMapping("/task/owner/{ownerId}")
  public JSONObject getTaskOrderByOwner(@PathVariable Long ownerId) {
    List<TaskOrder> taskOrders = taskOrderService.findByOwnerId(ownerId);
    return ResponseUtil.success(taskOrders);
  }

  @GetMapping("/task/accessor/{accessorId}")
  public JSONObject getTaskOrderByAccessor(@PathVariable Long accessorId) {
    List<TaskOrder> taskOrders = taskOrderService.findByAccessorId(accessorId);
    return ResponseUtil.success(taskOrders);
  }
  @GetMapping("/service/owner")
  public JSONObject getServiceOrderByOwner() {

    return ResponseUtil.success("");
  }
  @GetMapping("/service/accessor")
  public JSONObject getServiceOrderByAccessor() {

    return ResponseUtil.success("");
  }
}
