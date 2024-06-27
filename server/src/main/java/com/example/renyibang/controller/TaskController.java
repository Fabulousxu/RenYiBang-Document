package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {
    @Autowired TaskService taskService;

    @GetMapping("/search")
    public JSONObject searchTask(String keyword, int pageSize, int pageIndex)
    {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return taskService.searchTaskByPaging(keyword, pageable);
    }
}
