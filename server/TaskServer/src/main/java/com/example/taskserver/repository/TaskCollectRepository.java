package com.example.taskserver.repository;

import com.example.taskserver.entity.Task;
import com.example.taskserver.entity.TaskCollect;
import com.example.taskserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCollectRepository extends JpaRepository<TaskCollect, Long> {
    void deleteByTaskAndCollector(Task task, User uncollector);
}
