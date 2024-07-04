package com.example.renyibang.enums;

import lombok.Getter;

@Getter
public enum TaskStatus {
<<<<<<< Updated upstream
  UNPAID(0),
  IN_PROGRESS(1),
  COMPLETED(2),
  CONFIRMED(3),
  CANCELLED(4);

  private final int code;

  TaskStatus(int code) {
    this.code = code;
  }

  public static TaskStatus fromCode(int code) {
    for (TaskStatus status : TaskStatus.values()) {
      if (status.getCode() == code) {
        return status;
      }
    }
    throw new IllegalArgumentException("Unknown code: " + code);
  }
=======
    NORMAL(0),
    REMOVE(1),
    DELETE(2);

    private final int code;

    TaskStatus(int code) {
        this.code = code;
    }

    public static TaskStatus fromCode(int code) {
        for (TaskStatus status : TaskStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
>>>>>>> Stashed changes
}
