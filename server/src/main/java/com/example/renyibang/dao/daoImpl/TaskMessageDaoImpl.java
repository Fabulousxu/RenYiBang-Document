package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.TaskMessageDao;
import com.example.renyibang.entity.TaskMessage;
import com.example.renyibang.repository.TaskMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TaskMessageDaoImpl implements TaskMessageDao {
    @Autowired
    private TaskMessageRepository taskMessageRepository;
    @Override
    public Page<TaskMessage> getTaskMessages(long taskId, Pageable pageable)
    {
        return taskMessageRepository.findByTaskTaskId(taskId, pageable);
    }
}
