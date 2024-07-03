package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.ServiceMessageDao;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.ServiceMessage;
import com.example.renyibang.entity.User;
import com.example.renyibang.repository.ServiceMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceMessageDaoImpl implements ServiceMessageDao {
    @Autowired
    private ServiceMessageRepository serviceMessageRepository;

    @Autowired
    private UserDao userDao;

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
}
