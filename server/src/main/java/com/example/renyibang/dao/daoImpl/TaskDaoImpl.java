package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.TaskDao;
import com.example.renyibang.entity.Task;
import com.example.renyibang.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TaskDaoImpl implements TaskDao {
  @Autowired private TaskRepository taskRepository;
    @Override
    public Task findById(long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }
}
