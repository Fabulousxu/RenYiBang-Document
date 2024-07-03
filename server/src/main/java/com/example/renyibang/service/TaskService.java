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

    JSONObject likeMessage(long taskMessageId, long likerId);

    JSONObject unlikeMessage(long taskMessageId, long unlikerId);

    JSONObject collectTask(long taskId, long collectorId);

    JSONObject uncollectTask(long taskId, long uncollectorId);

    JSONObject accessTask(long taskId, long accessorId);

    JSONObject unaccessTask(long taskId, long unaccessorId);

    JSONObject publishMessage(long taskId, long userId, JSONObject body);

    JSONObject deleteMessage(long taskMessageId, long userId);
}
