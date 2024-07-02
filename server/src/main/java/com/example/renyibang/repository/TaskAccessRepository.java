package com.example.renyibang.repository;

import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskAccess;
import com.example.renyibang.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAccessRepository extends JpaRepository<TaskAccess, Long> {
    void deleteByTaskAndAccessor(Task task, User unaccessor);
}
