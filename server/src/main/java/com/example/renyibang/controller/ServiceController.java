package com.example.renyibang.controller;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.service.ServiceService;
import com.example.renyibang.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/service")
@CrossOrigin
public class ServiceController {
    @Autowired ServiceService serviceService;

    @GetMapping("/search")
    public JSONObject searchService(String keyword, int pageSize, int pageIndex, String order, String timeBegin, String timeEnd, long priceLow, long priceHigh)
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

        return serviceService.searchServiceByPaging(keyword, pageable, timeBegin, timeEnd, priceLow, priceHigh);
    }

    @GetMapping("/{serviceId}")
    public JSONObject getServiceInfo(@PathVariable long serviceId)
    {
        return serviceService.getServiceInfo(serviceId);
    }

    @GetMapping("/{serviceId}/comment")
    public JSONObject getServiceComment(@PathVariable long serviceId, int pageSize, int pageIndex, String order)
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

        return serviceService.getServiceComments(serviceId, pageable);
    }

    @GetMapping("/{serviceId}/message")
    public JSONObject getServiceMessage(@PathVariable long serviceId, int pageSize, int pageIndex, String order)
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

        return serviceService.getServiceMessages(serviceId, pageable);
    }

    @PutMapping("/comment/{serviceCommentId}/like")
    public JSONObject likeComment(@PathVariable long serviceCommentId)
    {
        //userId待替换
        long likerId = 1;

        return serviceService.likeComment(serviceCommentId, likerId);
    }

    @DeleteMapping("/comment/{serviceCommentId}/unlike")
    public JSONObject unlikeComment(@PathVariable long serviceCommentId)
    {
        //userId待替换
        long unlikerId = 1;

        return serviceService.unlikeComment(serviceCommentId, unlikerId);
    }

    @PutMapping("/message/{serviceMessageId}/like")
    public JSONObject likeMessage(@PathVariable long serviceMessageId)
    {
        //userId待替换
        long likerId = 1;
        return serviceService.likeMessage(serviceMessageId, likerId);
    }

    @DeleteMapping("/message/{serviceMessageId}/unlike")
    public JSONObject unlikeMessage(@PathVariable long serviceMessageId)
    {
        //userId待替换
        long unlikerId = 1;
        return serviceService.unlikeMessage(serviceMessageId, unlikerId);
    }

    @PutMapping("/{serviceId}/collect")
    public JSONObject collectServicce(@PathVariable long serviceId)
    {
        //userId待替换
        long collectorId = 1;
        return serviceService.collectService(serviceId, collectorId);
    }

    @DeleteMapping("/{serviceId}/uncollect")
    public JSONObject uncollectService(@PathVariable long serviceId)
    {
        //userId待替换
        long uncollectorId = 1;
        return serviceService.uncollectService(serviceId, uncollectorId);
    }

    @PutMapping("/{serviceId}/access")
    public JSONObject accessService(@PathVariable long serviceId, long userId)
    {
        //userId待替换
        long accessorId = 1;
        return serviceService.accessService(serviceId, userId);
    }

    @DeleteMapping("/{serviceId}/unaccess")
    public JSONObject unaccessService(@PathVariable long serviceId, long userId)
    {
        //userId待替换
        long unaccessorId = 1;
        return serviceService.unaccessService(serviceId, userId);
    }
}
