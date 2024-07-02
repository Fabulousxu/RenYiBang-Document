package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.service.TaskService;
import com.example.renyibang.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/task")
@CrossOrigin
public class TaskController {
    @Autowired TaskService taskService;

    @GetMapping("/search")
    public JSONObject searchTask(String keyword, int pageSize, int pageIndex, String order, String timeBegin, String timeEnd, long priceLow, long priceHigh)
    {
        Sort sort;

        if(Objects.equals(order, "time"))
        {
            sort = Sort.by("createdAt").descending();
        }

        else if (Objects.equals(order, "rating"))
        {
            sort = Sort.by("rating").descending();
        }

        else
        {
            return ResponseUtil.error("Undefined order");
        }

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        return taskService.searchTaskByPaging(keyword, pageable, timeBegin, timeEnd, priceLow, priceHigh);
    }

    @GetMapping("/{taskId}")
    public JSONObject getTaskInfo(@PathVariable long taskId)
    {
        return taskService.getTaskInfo(taskId);
    }

    @GetMapping("/{taskId}/comment")
    public JSONObject getTaskComment(@PathVariable long taskId, int pageSize, int pageIndex, String order)
    {
        Sort sort;

        if(Objects.equals(order, "likes"))
        {
            sort = Sort.by("likedNumber").descending();
        }

        else if (Objects.equals(order, "time"))
        {
            sort = Sort.by("createdAt").descending();
        }

        else
        {
            return ResponseUtil.error("Undefined order");
        }

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        return taskService.getTaskComments(taskId, pageable);
    }

    @GetMapping("/{taskId}/message")
    public JSONObject getTaskMessage(@PathVariable long taskId, int pageSize, int pageIndex, String order)
    {
        Sort sort;

        if(Objects.equals(order, "likes"))
        {
            sort = Sort.by("likedNumber").descending();
        }

        else if (Objects.equals(order, "time"))
        {
            sort = Sort.by("createdAt").descending();
        }

        else
        {
            return ResponseUtil.error("Undefined order");
        }

        Pageable pageable = PageRequest.of(pageIndex, pageSize, sort);

        return taskService.getTaskMessages(taskId, pageable);
    }

    @PutMapping("/comment/{taskCommentId}/like")
    public JSONObject likeComment(@PathVariable long taskCommentId)
    {
        //userId待替换
        long likerId = 1;

        return taskService.likeComment(taskCommentId, likerId);
    }
}
