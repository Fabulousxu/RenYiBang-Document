package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.TaskCommentDao;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.TaskComment;
import com.example.renyibang.entity.User;
import com.example.renyibang.repository.TaskCommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository

public class TaskCommentDaoImpl implements TaskCommentDao {
    @Autowired
    private TaskCommentRepository taskCommentRepository;

    @Autowired
    private UserDao userDao;

    @Override
    public Page<TaskComment> getTaskComments(long taskId, Pageable pageable)
    {
        return taskCommentRepository.findByTaskTaskId(taskId, pageable);
    }

    @SuppressWarnings("AlibabaTransactionMustHaveRollback")
    @Override
    @Transactional
    public boolean likeCommentByTaskCommentId(long taskCommentId, long likerId)
    {
        try
        {
            TaskComment taskComment = taskCommentRepository.findById(taskCommentId).orElseThrow(() -> new IllegalArgumentException("评论不存在！"));
            User liker = userDao.findById(likerId).orElseThrow(() -> new IllegalArgumentException("用户不存在！"));

            if(taskComment.isLikedByUser(liker))
            {
                return false;
            }

            else
            {
                taskComment.setLikedNumber(taskComment.getLikedNumber() + 1);
                taskComment.addLiker(liker);
                taskCommentRepository.save(taskComment);
                return true;
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
