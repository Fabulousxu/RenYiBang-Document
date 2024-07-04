package com.example.renyibang.dao;

import com.example.renyibang.entity.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskDao {
  Task findById(long taskId);

  Page<Task> searchTaskByPaging(String keyword, Pageable pageable, LocalDateTime beginDateTime, LocalDateTime endDateTime, long priceLow, long priceHigh);

  String collectTaskByTaskId(long taskId, long collectorId);

  String uncollectTaskByTaskId(long taskId, long uncollectorId);

  String accessTaskByTaskId(long taskId, long accessorId);

  String unaccessTaskByTaskId(long taskId, long unaccessorId);

  String publishTask(long userId, String title, String description, long price, List<String> requestImages);
}
