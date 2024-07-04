package com.example.taskserver.dao;

import com.example.taskserver.entity.TaskMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskMessageDao {
    Page<TaskMessage> getTaskMessages(long taskId, Pageable pageable);

    String likeMessageByTaskMessageId(long taskMessageId, long likerId);

    String unlikeMessageByTaskMessageId(long taskMessageId, long unlikerId);

    String putMessage(long taskId, long userId, String content);

    String deleteMessage(long taskMessageId, long userId);
}
