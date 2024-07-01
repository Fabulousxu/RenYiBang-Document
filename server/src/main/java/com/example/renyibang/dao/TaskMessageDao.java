package com.example.renyibang.dao;

import com.example.renyibang.entity.TaskMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMessageDao {
    Page<TaskMessage> getTaskMessages(long taskId, Pageable pageable);
}
