package com.example.taskserver.repository;

import com.example.taskserver.entity.Task;
import com.example.taskserver.entity.TaskAccess;
import com.example.taskserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskAccessRepository extends JpaRepository<TaskAccess, Long> {
    void deleteByTaskAndAccessor(Task task, User unaccessor);
}
