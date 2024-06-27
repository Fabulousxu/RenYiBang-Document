package com.example.renyibang.controller;

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
  public String getTaskOrderByOwner(@PathVariable Long ownerId) {
    List<TaskOrder> taskOrders = taskOrderService.findByOwnerId(ownerId);
    return ResponseUtil.success(taskOrders).toJSONString();
  }

  @GetMapping("/task/accessor/{accessorId}")
  public String getTaskOrderByAccessor(@PathVariable Long accessorId) {
    List<TaskOrder> taskOrders = taskOrderService.findByAccessorId(accessorId);
    return ResponseUtil.success(taskOrders).toJSONString();
  }
  @GetMapping("/service/owner")
  public String getServiceOrderByOwner() {

    return "getServiceOrderByOwner";
  }
  @GetMapping("/service/accessor")
  public String getServiceOrderByAccessor() {

    return "getServiceOrderByAccessor";
  }
}
