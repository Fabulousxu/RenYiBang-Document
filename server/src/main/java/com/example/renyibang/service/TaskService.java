package com.example.renyibang.service;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TaskService {


    JSONObject searchTaskByPaging(String keyword, Pageable pageable, String timeBegin, String timeEnd, long priceLow, long priceHigh);

    JSONObject getTaskInfo(long taskId);

    JSONObject getTaskComments(long taskId, Pageable pageable);

    JSONObject getTaskMessages(long taskId, Pageable pageable);

    JSONObject likeComment(long taskCommentId, long likerId);

    JSONObject unlikeComment(long taskCommentId, long unlikerId);
}
