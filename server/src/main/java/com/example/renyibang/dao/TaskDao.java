package com.example.renyibang.dao;

import com.example.renyibang.entity.Task;

public interface TaskDao {
  Task findById(long taskId);
}
