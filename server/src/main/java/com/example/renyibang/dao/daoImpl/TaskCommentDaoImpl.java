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

    @Override
    public String likeCommentByTaskCommentId(long taskCommentId, long likerId)
    {
        try
        {
            TaskComment taskComment = taskCommentRepository.findById(taskCommentId).orElse(null);
            if(taskComment == null)
            {
                return "评论不存在！";
            }
            User liker = userDao.findById(likerId).orElse(null);
            if(liker == null)
            {
                return "用户不存在！";
            }

            if(taskComment.isLikedByUser(liker))
            {
                return "用户已点赞过该评论！";
            }

            else
            {
                taskComment.setLikedNumber(taskComment.getLikedNumber() + 1);
                taskComment.addLiker(liker);
                taskCommentRepository.save(taskComment);
                return "点赞成功！";
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String unlikeCommentByTaskCommentId(long taskCommentId, long unlikerId)
    {
        try
        {
            TaskComment taskComment = taskCommentRepository.findById(taskCommentId).orElse(null);
            if(taskComment == null)
            {
                return "评论不存在！";
            }
            User unliker = userDao.findById(unlikerId).orElse(null);
            if(unliker == null)
            {
                return "用户不存在！";
            }

            if(!taskComment.isLikedByUser(unliker))
            {
                return "用户未点赞过该评论！";
            }

            else
            {
                //存在并发问题!!
                taskComment.setLikedNumber(taskComment.getLikedNumber() - 1);
                taskComment.removeLiker(unliker);
                taskCommentRepository.save(taskComment);
                return "取消点赞成功！";
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
