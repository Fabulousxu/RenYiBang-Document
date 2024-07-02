package com.example.renyibang.dao;

import com.example.renyibang.entity.TaskComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCommentDao {
    Page<TaskComment> getTaskComments(long taskId, Pageable pageable);

    boolean likeCommentByTaskCommentId(long taskCommentId, long likerId);
}
