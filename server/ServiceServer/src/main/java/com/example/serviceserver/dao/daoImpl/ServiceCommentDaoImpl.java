package com.example.serviceserver.dao.daoImpl;

import com.example.serviceserver.dao.ServiceCommentDao;
import com.example.serviceserver.dao.UserDao;
import com.example.serviceserver.repository.ServiceCommentRepository;
import com.example.serviceserver.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ServiceCommentDaoImpl implements ServiceCommentDao {
    @Autowired
    private ServiceCommentRepository serviceCommentRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ServiceRepository serviceRepository;

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

    @Override
    public String putComment(long serviceId, long userId, String content, byte rating)
    {
        try
        {
            User commenter = userDao.findById(userId).orElse(null);
            if(commenter == null)
            {
                return "用户不存在！";
            }

            Service service = serviceRepository.findById(serviceId).orElse(null);
            if(service == null)
            {
                return "服务不存在！";
            }

            if(service.isCommented(commenter))
            {
                return "用户已经评论过该服务！";
            }

            ServiceComment serviceComment = new ServiceComment();
            serviceComment.setCommenter(commenter);
            serviceComment.setService(service);
            serviceComment.setContent(content);
            serviceComment.setCreatedAt(LocalDateTime.now());
            serviceComment.setRating(rating);

            serviceCommentRepository.save(serviceComment);

            return "发布评论成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String deleteComment(long serviceCommentId, long userId)
    {
        try
        {
            User commenter = userDao.findById(userId).orElse(null);
            if(commenter == null)
            {
                return "用户不存在！";
            }

            ServiceComment serviceComment = serviceCommentRepository.findById(serviceCommentId).orElse(null);
            if(serviceComment == null)
            {
                return "评论不存在！";
            }

            if(!serviceComment.isCommenter(commenter))
            {
                return "该评论不是由此用户发布！";
            }

            serviceCommentRepository.deleteById(serviceCommentId);

            return "删除评论成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
