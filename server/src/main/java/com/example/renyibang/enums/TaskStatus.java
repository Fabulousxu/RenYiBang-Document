package com.example.renyibang.enums;

public enum TaskStatus {
  UNPAID(0),
  PAID(1),
  COMPLETED(2),
  CONFIRMED(3),
  CANCELLED(4);

  private final int code;

  TaskStatus(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public static TaskStatus fromCode(int code) {
    for (TaskStatus status : TaskStatus.values()) {
      if (status.getCode() == code) {
        return status;
      }
    }
    throw new IllegalArgumentException("Unknown code: " + code);
  }
}
