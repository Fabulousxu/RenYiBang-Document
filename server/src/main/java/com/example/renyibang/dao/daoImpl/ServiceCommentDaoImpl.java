package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.ServiceCommentDao;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.ServiceComment;
import com.example.renyibang.entity.User;
import com.example.renyibang.repository.ServiceCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceCommentDaoImpl implements ServiceCommentDao {
    @Autowired
    private ServiceCommentRepository serviceCommentRepository;

    @Autowired
    private UserDao userDao;

    @Override
    public Page<ServiceComment> getServiceComments(long serviceId, Pageable pageable)
    {
        return serviceCommentRepository.findByServiceServiceId(serviceId, pageable);
    }

    @Override
    public String likeCommentByServiceCommentId(long serviceCommentId, long likerId)
    {
        try
        {
            ServiceComment serviceComment = serviceCommentRepository.findById(serviceCommentId).orElse(null);
            if(serviceComment == null)
            {
                return "评论不存在！";
            }
            User liker = userDao.findById(likerId).orElse(null);
            if(liker == null)
            {
                return "用户不存在！";
            }

            if(serviceComment.isLikedByUser(liker))
            {
                return "用户已点赞过该服务！";
            }

            else
            {
                serviceComment.setLikedNumber(serviceComment.getLikedNumber() + 1);
                serviceComment.addLiker(liker);
                serviceCommentRepository.save(serviceComment);
                return "点赞成功！";
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String unlikeCommentByServiceCommentId(long serviceCommentId, long unlikerId)
    {
        try
        {
            ServiceComment serviceComment = serviceCommentRepository.findById(serviceCommentId).orElse(null);
            if(serviceComment == null)
            {
                return "评论不存在！";
            }
            User unliker = userDao.findById(unlikerId).orElse(null);
            if(unliker == null)
            {
                return "用户不存在！";
            }

            if(!serviceComment.isLikedByUser(unliker))
            {
                return "用户未点赞过该评论！";
            }

            else
            {
                //存在并发问题!!
                serviceComment.setLikedNumber(serviceComment.getLikedNumber() - 1);
                serviceComment.removeLiker(unliker);
                serviceCommentRepository.save(serviceComment);
                return "取消点赞成功！";
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
