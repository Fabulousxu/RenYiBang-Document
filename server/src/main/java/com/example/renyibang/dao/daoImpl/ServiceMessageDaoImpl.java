package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.ServiceMessageDao;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.*;
import com.example.renyibang.repository.ServiceMessageRepository;
import com.example.renyibang.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ServiceMessageDaoImpl implements ServiceMessageDao {
    @Autowired
    private ServiceMessageRepository serviceMessageRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Page<ServiceMessage> getServiceMessages(long serviceId, Pageable pageable)
    {
        return serviceMessageRepository.findByServiceServiceId(serviceId, pageable);
    }

    @Override
    public String likeMessageByServiceMessageId(long serviceMessageId, long likerId)
    {
        try
        {
            ServiceMessage serviceMessage = serviceMessageRepository.findById(serviceMessageId).orElse(null);
            if(serviceMessage == null)
            {
                return "留言不存在！";
            }
            User liker = userDao.findById(likerId).orElse(null);
            if(liker == null)
            {
                return "用户不存在！";
            }

            if(serviceMessage.isLikedByUser(liker))
            {
                return "用户已点赞过该留言！";
            }

            else
            {
                serviceMessage.setLikedNumber(serviceMessage.getLikedNumber() + 1);
                serviceMessage.addLiker(liker);
                serviceMessageRepository.save(serviceMessage);
                return "点赞成功！";
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String unlikeMessageByServiceMessageId(long serviceMessageId, long unlikerId)
    {
        try
        {
            ServiceMessage serviceMessage = serviceMessageRepository.findById(serviceMessageId).orElse(null);
            if(serviceMessage == null)
            {
                return "留言不存在！";
            }
            User unliker = userDao.findById(unlikerId).orElse(null);
            if(unliker == null)
            {
                return "用户不存在！";
            }

            if(!serviceMessage.isLikedByUser(unliker))
            {
                return "用户未点赞过该留言！";
            }

            else
            {
                //存在并发问题!!
                serviceMessage.setLikedNumber(serviceMessage.getLikedNumber() - 1);
                serviceMessage.removeLiker(unliker);
                serviceMessageRepository.save(serviceMessage);
                return "取消点赞成功！";
            }
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String putMessage(long serviceId, long userId, String content)
    {
        try
        {
            User messager = userDao.findById(userId).orElse(null);
            if(messager == null)
            {
                return "用户不存在！";
            }

            Service service = serviceRepository.findById(serviceId).orElse(null);
            if(service == null)
            {
                return "服务不存在！";
            }

            ServiceMessage serviceMessage = new ServiceMessage();
            serviceMessage.setService(service);
            serviceMessage.setMessager(messager);
            serviceMessage.setContent(content);
            serviceMessage.setCreatedAt(LocalDateTime.now());
            serviceMessageRepository.save(serviceMessage);

            return "发布留言成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String deleteMessage(long serviceMessageId, long userId)
    {
        try
        {
            User messager = userDao.findById(userId).orElse(null);
            if(messager == null)
            {
                return "用户不存在！";
            }

            ServiceMessage serviceMessage = serviceMessageRepository.findById(serviceMessageId).orElse(null);
            if(serviceMessage == null)
            {
                return "留言不存在！";
            }

            if(!serviceMessage.isMessager(messager))
            {
                return "该留言不是由此用户发布！";
            }

            serviceMessageRepository.deleteById(serviceMessageId);

            return "删除留言成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
