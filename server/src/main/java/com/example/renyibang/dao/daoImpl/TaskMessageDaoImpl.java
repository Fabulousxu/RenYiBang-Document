package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.TaskMessageDao;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.TaskComment;
import com.example.renyibang.entity.TaskMessage;
import com.example.renyibang.entity.User;
import com.example.renyibang.repository.TaskMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TaskMessageDaoImpl implements TaskMessageDao {
    @Autowired
    private TaskMessageRepository taskMessageRepository;

    @Autowired
    private UserDao userDao;

    @Override
    public Page<TaskMessage> getTaskMessages(long taskId, Pageable pageable)
    {
        return taskMessageRepository.findByTaskTaskId(taskId, pageable);
    }

    @Override
    public String likeMessageByTaskMessageId(long taskMessageId, long likerId)
    {
        try
        {
            TaskMessage taskMessage = taskMessageRepository.findById(taskMessageId).orElse(null);
            if(taskMessage == null)
            {
                return "留言不存在！";
            }
            User liker = userDao.findById(likerId).orElse(null);
            if(liker == null)
            {
                return "用户不存在！";
            }

            if(taskMessage.isLikedByUser(liker))
            {
                return "用户已点赞过该留言！";
            }

            else
            {
                taskMessage.setLikedNumber(taskMessage.getLikedNumber() + 1);
                taskMessage.addLiker(liker);
                taskMessageRepository.save(taskMessage);
                return "点赞成功！";
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String unlikeMessageByTaskMessageId(long taskMessageId, long unlikerId)
    {
        try
        {
            TaskMessage taskMessage = taskMessageRepository.findById(taskMessageId).orElse(null);
            if(taskMessage == null)
            {
                return "留言不存在！";
            }
            User unliker = userDao.findById(unlikerId).orElse(null);
            if(unliker == null)
            {
                return "用户不存在！";
            }

            if(!taskMessage.isLikedByUser(unliker))
            {
                return "用户未点赞过该留言！";
            }

            else
            {
                //存在并发问题!!
                taskMessage.setLikedNumber(taskMessage.getLikedNumber() - 1);
                taskMessage.removeLiker(unliker);
                taskMessageRepository.save(taskMessage);
                return "取消点赞成功！";
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
