package com.example.renyibang.service.serviceImpl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.dao.*;
import com.example.renyibang.entity.*;
import com.example.renyibang.service.ServiceService;
import com.example.renyibang.util.DateTimeUtil;
import com.example.renyibang.util.PriceUtil;
import com.example.renyibang.util.ResponseUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    ServiceDao serviceDao;

    @Autowired
    UserDao userDao;

    @Autowired
    ServiceCommentDao serviceCommentDao;

    @Autowired
    ServiceMessageDao serviceMessageDao;

    @Override
    public JSONObject searchServiceByPaging(String keyword, Pageable pageable, String timeBegin, String timeEnd, long priceLow, long priceHigh)
    {
        try{
            JSONArray result = new JSONArray();
            Page<Service> searchResult = serviceDao.searchServiceByPaging(keyword, pageable, DateTimeUtil.getBeginDateTime(timeBegin), DateTimeUtil.getEndDateTime(timeEnd), priceLow, PriceUtil.priceConvert(priceHigh));

            for(Service service : searchResult.getContent())
            {
                //需要传入userId
                result.add(service.toJSON());
            }

            JSONObject returnRes = new JSONObject();
            returnRes.put("total", searchResult.getTotalElements());
            returnRes.put("items", result);

            return ResponseUtil.success(returnRes);
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject getServiceInfo(long serviceId)
    {
        try
        {
            Service result = serviceDao.findById(serviceId);

            if(result == null)
            {
                return ResponseUtil.error("服务信息为null");
            }

            else
            {
                return ResponseUtil.success(result.toJSON());
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject getServiceComments(long serviceId, Pageable pageable)
    {
        try
        {
            JSONArray result = new JSONArray();
            Page<ServiceComment> getResult = serviceCommentDao.getServiceComments(serviceId, pageable);

            for(ServiceComment serviceComment : getResult)
            {
                result.add(serviceComment.toJSON());
            }

            JSONObject returnRes = new JSONObject();
            returnRes.put("total", getResult.getTotalElements());
            returnRes.put("items", result);

            return ResponseUtil.success(returnRes);
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject getServiceMessages(long serviceId, Pageable pageable)
    {
        try
        {
            JSONArray result = new JSONArray();
            Page<ServiceMessage> getResult = serviceMessageDao.getServiceMessages(serviceId, pageable);

            for(ServiceMessage serviceMessage : getResult)
            {
                result.add(serviceMessage.toJSON());
            }

            JSONObject returnRes = new JSONObject();
            returnRes.put("total", getResult.getTotalElements());
            returnRes.put("items", result);

            return ResponseUtil.success(returnRes);
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Transactional
    @Override
    public JSONObject likeComment(long serviceCommentId, long likerId)
    {
        try
        {
            String result = serviceCommentDao.likeCommentByServiceCommentId(serviceCommentId, likerId);
            if("点赞成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject unlikeComment(long serviceCommentId, long unlikerId)
    {
        try
        {
            String result = serviceCommentDao.unlikeCommentByServiceCommentId(serviceCommentId, unlikerId);
            if("取消点赞成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject likeMessage(long serviceMessageId, long likerId)
    {
        try
        {
            String result = serviceMessageDao.likeMessageByServiceMessageId(serviceMessageId, likerId);
            if("点赞成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject unlikeMessage(long serviceMessageId, long unlikerId)
    {
        try
        {
            String result = serviceMessageDao.unlikeMessageByServiceMessageId(serviceMessageId, unlikerId);
            if("取消点赞成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject collectService(long serviceId, long collectorId)
    {
        try
        {
            String result = serviceDao.collectServiceByServiceId(serviceId, collectorId);
            if("收藏成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject uncollectService(long serviceId, long uncollectorId)
    {
        try
        {
            String result = serviceDao.uncollectServiceByServiceId(serviceId, uncollectorId);
            if("取消收藏成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject accessService(long serviceId, long accessorId)
    {
        try
        {
            String result = serviceDao.accessServiceByServiceId(serviceId, accessorId);
            if("购买服务成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e.getMessage()));
        }
    }

    @Transactional
    @Override
    public JSONObject unaccessService(long serviceId, long unaccessorId)
    {
        try
        {
            String result = serviceDao.unaccessServiceByServiceId(serviceId, unaccessorId);
            if("取消购买服务成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject publishMessage(long serviceId, long userId, JSONObject body)
    {
        try
        {
            Object requestContent = body.get("content");
            if(requestContent == null)
            {
                return ResponseUtil.error("请求体为空！");
            }

            String content = requestContent.toString();
            if(content.isEmpty())
            {
                return ResponseUtil.error("留言内容为空！");
            }
            String result = serviceMessageDao.putMessage(serviceId, userId, content);
            if("发布留言成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject deleteMessage(long serviceMessageId, long userId)
    {
        try
        {
            String result = serviceMessageDao.deleteMessage(serviceMessageId, userId);
            if("删除留言成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject publishComment(long serviceId, long userId, JSONObject body)
    {
        try
        {
            Object requestContent = body.get("content");
            Object requestRating = body.get("rating");
            if(requestContent == null || requestRating == null)
            {
                return ResponseUtil.error("请求体不完整！");
            }

            String content = requestContent.toString();
            if(content.isEmpty())
            {
                return ResponseUtil.error("评论内容为空！");
            }

            byte rating = body.getByteValue("rating");;

            String result = serviceCommentDao.putComment(serviceId, userId, content, rating);
            if("发布评论成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }

    @Override
    public JSONObject deleteComment(long serviceCommentId, long userId)
    {
        try
        {
            String result = serviceCommentDao.deleteComment(serviceCommentId, userId);
            if("删除评论成功！".equals(result))
            {
                return ResponseUtil.success(result);
            }

            else
            {
                return ResponseUtil.error(result);
            }
        }
        catch (Exception e)
        {
            return ResponseUtil.error(String.valueOf(e));
        }
    }
}
