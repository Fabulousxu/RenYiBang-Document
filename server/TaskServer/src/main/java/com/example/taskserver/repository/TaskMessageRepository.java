package com.example.taskserver.repository;

import com.example.taskserver.entity.TaskMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMessageRepository extends JpaRepository<TaskMessage, Long> {
    Page<TaskMessage> findByTaskTaskId(long taskId, Pageable pageable);
}
