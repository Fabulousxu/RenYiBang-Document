package com.example.renyibang.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {

    JSONObject searchTaskByPaging(String keyword, Pageable pageable);

}
