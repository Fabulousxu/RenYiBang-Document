package com.example.renyibang.enums;

public enum TaskStatus {
  UNPAID("未付款"),
  IN_PROGRESS("已付款，任务进行中"),
  RECEIVER_COMPLETED("接收者已完成，等待发布者确认"),
  PUBLISHER_CONFIRMED("发布者已确认完成"),
  ORDER_CANCELLED("订单已取消");

  private final String description;

  TaskStatus(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
