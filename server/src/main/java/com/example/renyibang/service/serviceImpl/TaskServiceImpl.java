package com.example.renyibang.service.serviceImpl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.dao.TaskDao;
import com.example.renyibang.entity.Task;
import com.example.renyibang.service.TaskService;
import com.example.renyibang.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskDao taskDao;

    @Override
    public JSONObject searchTaskByPaging(String keyword, Pageable pageable)
    {
        try{
            JSONArray result = new JSONArray();
            for(Task task : taskDao.searchTaskByPaging(keyword, pageable))
            {
                result.add(task.toJSON());
            }

            return ResponseUtil.success(result);
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

}
