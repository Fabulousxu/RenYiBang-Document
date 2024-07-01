package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.TaskCommentDao;
import com.example.renyibang.entity.TaskComment;
import com.example.renyibang.repository.TaskCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository

public class TaskCommentDaoImpl implements TaskCommentDao {
    @Autowired
    private TaskCommentRepository taskCommentRepository;
    @Override
    public Page<TaskComment> getTaskComments(long taskId, Pageable pageable)
    {
        return taskCommentRepository.findByTaskTaskId(taskId, pageable);
    }
}
