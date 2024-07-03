package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.service.ServiceService;
import com.example.renyibang.service.TaskService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public class IssueController {
    @Autowired ServiceService serviceService;
    @Autowired TaskService taskService;

    @PostMapping("api/issue/service")
    public JSONObject issueService(@RequestAttribute Claims claims, @RequestBody JSONObject serviceObject)
    {
        String title = serviceObject.getString("title");
        String description = serviceObject.getString("description");
        String price = serviceObject.getString("price");
        JSONArray images = serviceObject.getJSONArray("images");
        return serviceService.issueService(claims.get("userId", Long.class), title, description, price, images);
    }
}
