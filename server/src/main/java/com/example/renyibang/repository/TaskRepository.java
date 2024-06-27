package com.example.renyibang.repository;

import com.example.renyibang.entity.Task;
import com.example.renyibang.enums.TaskStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByOwnerId(long ownerId);

    List<Task> findByStatus(TaskStatus status);

    Task findTaskById(long taskId);

    List<Task> findAllTasks();

    long createTask(long ownerId, String title, String description, long reward);

    boolean markTaskStatus(long taskId, TaskStatus status);

    boolean checkTaskExist(long taskId);

    boolean checkTaskStatus(long taskId, TaskStatus status);
}
