package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.TaskCommentDao;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskComment;
import com.example.renyibang.entity.User;
import com.example.renyibang.repository.TaskCommentRepository;
import com.example.renyibang.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository

public class TaskCommentDaoImpl implements TaskCommentDao {
    @Autowired
    private TaskCommentRepository taskCommentRepository;

    @Autowired
    private UserDao userDao;

    @Autowired TaskRepository taskRepository;

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

    @Override
    public String putComment(long taskId, long userId, String content)
    {
        try
        {
            User commenter = userDao.findById(userId).orElse(null);
            if(commenter == null)
            {
                return "用户不存在！";
            }

            Task task = taskRepository.findById(taskId).orElse(null);
            if(task == null)
            {
                return "任务不存在！";
            }

            if(task.isCommented(commenter))
            {
                return "用户已经评论过该任务！";
            }

            TaskComment taskComment = new TaskComment();
            taskComment.setCommenter(commenter);
            taskComment.setTask(task);
            taskComment.setContent(content);
            taskComment.setCreatedAt(LocalDateTime.now());

            taskCommentRepository.save(taskComment);

            return "发布评论成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String deleteComment(long taskCommentId, long userId)
    {
        try
        {
            User commenter = userDao.findById(userId).orElse(null);
            if(commenter == null)
            {
                return "用户不存在！";
            }

            TaskComment taskComment = taskCommentRepository.findById(taskCommentId).orElse(null);
            if(taskComment == null)
            {
                return "评论不存在！";
            }

            if(!taskComment.isCommenter(commenter))
            {
                return "该评论不是由此用户发布！";
            }

            taskCommentRepository.deleteById(taskCommentId);

            return "删除评论成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
