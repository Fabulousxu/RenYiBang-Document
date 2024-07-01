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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
            Task result = taskDao.getTask(taskId);

            if(result == null)
            {
                return ResponseUtil.error("Get task information null.");
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
}
