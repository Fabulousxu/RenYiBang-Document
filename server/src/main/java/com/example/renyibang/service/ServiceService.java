package com.example.renyibang.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ServiceService {
    JSONObject searchServiceByPaging(String keyword, Pageable pageable, String timeBegin, String timeEnd, long priceLow, long priceHigh);

    JSONObject getServiceInfo(long serviceId);

    JSONObject getServiceComments(long serviceId, Pageable pageable);

    JSONObject getServiceMessages(long serviceId, Pageable pageable);

    JSONObject likeComment(long serviceCommentId, long likerId);

    JSONObject unlikeComment(long serviceCommentId, long unlikerId);

    JSONObject likeMessage(long serviceMessageId, long likerId);

    JSONObject unlikeMessage(long serviceMessageId, long unlikerId);

    JSONObject collectService(long serviceId, long collectorId);

    JSONObject uncollectService(long serviceId, long uncollectorId);

    JSONObject accessService(long serviceId, long accessorId);

    JSONObject unaccessService(long serviceId, long unaccessorId);

    JSONObject publishMessage(long serviceId, long userId, JSONObject body);

    JSONObject deleteMessage(long serviceMessageId, long userId);

    JSONObject publishComment(long serviceId, long userId, JSONObject body);

    JSONObject deleteComment(long serviceCommentId, long userId);

    JSONObject publishService(long userId, JSONObject body);
}
