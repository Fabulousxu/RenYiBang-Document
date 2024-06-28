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
@RequestMapping("/task")
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
}
