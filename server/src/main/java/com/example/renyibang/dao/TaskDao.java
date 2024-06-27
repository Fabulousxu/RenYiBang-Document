package com.example.renyibang.dao;

import com.example.renyibang.entity.Task;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao {
  List<Task> searchTaskByPaging(String keyword, Pageable pageable);
  Task findById(long taskId);
}
