package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.util.ResponseUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/help")
public class HelpController {
    @PostMapping("/test1")
    public JSONObject testForNormal() {
        return ResponseUtil.success("success");
    }

    @PostMapping("/test2")
    public JSONObject testForWaiter() {
        return ResponseUtil.success("success");
    }

    @PostMapping("/test3")
    public JSONObject testForAdmin() {
        return ResponseUtil.success("success");
    }

}
