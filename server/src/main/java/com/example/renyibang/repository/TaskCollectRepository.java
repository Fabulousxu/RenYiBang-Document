package com.example.renyibang.repository;

import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskCollect;
import com.example.renyibang.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCollectRepository extends JpaRepository<TaskCollect, Long> {
    void deleteByTaskAndCollector(Task task, User uncollector);
}
