package com.example.renyibang.service.serviceImpl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.dao.TaskCommentDao;
import com.example.renyibang.dao.TaskDao;
import com.example.renyibang.dao.TaskMessageDao;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskComment;
import com.example.renyibang.entity.TaskMessage;
import com.example.renyibang.service.TaskService;
import com.example.renyibang.util.DateTimeUtil;
import com.example.renyibang.util.PriceUtil;
import com.example.renyibang.util.ResponseUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDao taskDao;

    @Autowired
    UserDao userDao;

    @Autowired
    TaskCommentDao taskCommentDao;

    @Autowired
    TaskMessageDao taskMessageDao;

    @Override
    public JSONObject searchTaskByPaging(String keyword, Pageable pageable, String timeBegin, String timeEnd, long priceLow, long priceHigh)
    {
        try{
            JSONArray result = new JSONArray();
            Page<Task> searchResult = taskDao.searchTaskByPaging(keyword, pageable, DateTimeUtil.getBeginDateTime(timeBegin), DateTimeUtil.getEndDateTime(timeEnd), priceLow, PriceUtil.priceConvert(priceHigh));

            for(Task task : searchResult.getContent())
            {
                //需要传入userId
                result.add(task.toJSON());
            }

            JSONObject returnRes = new JSONObject();
            returnRes.put("total", searchResult.getTotalElements());
            returnRes.put("items", result);

            return ResponseUtil.success(returnRes);
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject getTaskInfo(long taskId)
    {
        try
        {
            Task result = taskDao.findById(taskId);

            if(result == null)
            {
                return ResponseUtil.error("任务信息为null");
            }

            else
            {
                return ResponseUtil.success(result.toJSON());
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject getTaskComments(long taskId, Pageable pageable)
    {
        try
        {
            JSONArray result = new JSONArray();
            Page<TaskComment> getResult = taskCommentDao.getTaskComments(taskId, pageable);

            for(TaskComment taskComment : getResult)
            {
                result.add(taskComment.toJSON());
            }

            JSONObject returnRes = new JSONObject();
            returnRes.put("total", getResult.getTotalElements());
            returnRes.put("items", result);

            return ResponseUtil.success(returnRes);
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject getTaskMessages(long taskId, Pageable pageable)
    {
        try
        {
            JSONArray result = new JSONArray();
            Page<TaskMessage> getResult = taskMessageDao.getTaskMessages(taskId, pageable);

            for(TaskMessage taskMessage : getResult)
            {
                result.add(taskMessage.toJSON());
            }

            JSONObject returnRes = new JSONObject();
            returnRes.put("total", getResult.getTotalElements());
            returnRes.put("items", result);

            return ResponseUtil.success(returnRes);
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Transactional
    @Override
    public JSONObject likeComment(long taskCommentId, long likerId)
    {
        try
        {
            String result = taskCommentDao.likeCommentByTaskCommentId(taskCommentId, likerId);
            if("点赞成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject unlikeComment(long taskCommentId, long unlikerId)
    {
        try
        {
            String result = taskCommentDao.unlikeCommentByTaskCommentId(taskCommentId, unlikerId);
            if("取消点赞成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject likeMessage(long taskMessageId, long likerId)
    {
        try
        {
            String result = taskMessageDao.likeMessageByTaskMessageId(taskMessageId, likerId);
            if("点赞成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject unlikeMessage(long taskMessageId, long unlikerId)
    {
        try
        {
            String result = taskMessageDao.unlikeMessageByTaskMessageId(taskMessageId, unlikerId);
            if("取消点赞成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject collectTask(long taskId, long collectorId)
    {
        try
        {
            String result = taskDao.collectTaskByTaskId(taskId, collectorId);
            if("收藏成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject uncollectTask(long taskId, long uncollectorId)
    {
        try
        {
            String result = taskDao.uncollectTaskByTaskId(taskId, uncollectorId);
            if("取消收藏成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject accessTask(long taskId, long accessorId)
    {
        try
        {
            String result = taskDao.accessTaskByTaskId(taskId, accessorId);
            if("接取任务成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject unaccessTask(long taskId, long unaccessorId)
    {
        try
        {
            String result = taskDao.unaccessTaskByTaskId(taskId, unaccessorId);
            if("取消接取任务成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject publishMessage(long taskId, long userId, JSONObject body)
    {
        try
        {
            Object requestContent = body.get("content");
            if(requestContent == null)
            {
                return ResponseUtil.error("请求体为空！");
            }

            String content = requestContent.toString();
            if(content.isEmpty())
            {
                return ResponseUtil.error("留言内容为空！");
            }
            String result = taskMessageDao.putMessage(taskId, userId, content);
            if("发布留言成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject deleteMessage(long taskMessageId, long userId)
    {
        try
        {
            String result = taskMessageDao.deleteMessage(taskMessageId, userId);
            if("删除留言成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject publishComment(long taskId, long userId, JSONObject body)
    {
        try
        {
            Object requestContent = body.get("content");
            if(requestContent == null)
            {
                return ResponseUtil.error("请求体为空！");
            }

            String content = requestContent.toString();
            if(content.isEmpty())
            {
                return ResponseUtil.error("评论内容为空！");
            }
            String result = taskCommentDao.putComment(taskId, userId, content);
            if("发布评论成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject deleteComment(long taskCommentId, long userId)
    {
        try
        {
            String result = taskCommentDao.deleteComment(taskCommentId, userId);
            if("删除评论成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }
}
