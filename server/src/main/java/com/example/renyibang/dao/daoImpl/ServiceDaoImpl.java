package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.ServiceDao;
import com.example.renyibang.entity.*;
import com.example.renyibang.repository.*;
import com.example.renyibang.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ServiceDaoImpl implements ServiceDao {
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ServiceCollectRepository serviceCollectRepository;

    @Autowired
    ServiceAccessRepository serviceAccessRepository;

    @Override
    public Service findById(long serviceId) {
        return serviceRepository.findById(serviceId).orElse(null);
    }

    @Override
    public Page<Service> searchServiceByPaging(String keyword, Pageable pageable, LocalDateTime beginDateTime, LocalDateTime endDateTime, long priceLow, long priceHigh)
    {
        if(!keyword.isEmpty())
        {
            return serviceRepository.searchServices(keyword, priceLow, priceHigh, beginDateTime, endDateTime, pageable);
        }
        else
        {
            return serviceRepository.findByPriceBetweenAndCreatedAtBetween(priceLow, priceHigh, beginDateTime, endDateTime, pageable);
        }
    }
    
    @Override
    public String collectServiceByServiceId(long serviceId, long collectorId)
    {
        try{
            User collector = userRepository.findById(collectorId).orElse(null);
            if(collector == null)
            {
                return "用户不存在！";
            }

            Service service = serviceRepository.findById(serviceId).orElse(null);
            if(service == null)
            {
                return "服务不存在！";
            }

            if(collector.hasCollected(service))
            {
                return "用户已收藏该服务！";
            }

            service.setCollectedNumber(service.getCollectedNumber() + 1);
            ServiceCollect serviceCollect = new ServiceCollect();
            serviceCollect.setService(service);
            serviceCollect.setCollector(collector);
            serviceCollect.setCreatedAt(LocalDateTime.now());

            serviceCollectRepository.save(serviceCollect);

            return "收藏成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String uncollectServiceByServiceId(long serviceId, long uncollectorId)
    {
        try
        {
            User uncollector = userRepository.findById(uncollectorId).orElse(null);
            if(uncollector == null)
            {
                return "用户不存在！";
            }

            Service service = serviceRepository.findById(serviceId).orElse(null);
            if(service == null)
            {
                return "服务不存在！";
            }

            if(!uncollector.hasCollected(service))
            {
                return "用户未收藏该服务！";
            }

            service.setCollectedNumber(service.getCollectedNumber() - 1);
            serviceCollectRepository.deleteByServiceAndCollector(service, uncollector);

            return "取消收藏成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String accessServiceByServiceId(long serviceId, long accessorId)
    {
        try
        {
            Service service = serviceRepository.findById(serviceId).orElse(null);
            if(service == null)
            {
                return "服务不存在！";
            }

            User accessor = userRepository.findById(accessorId).orElse(null);
            if(accessor == null)
            {
                return "用户不存在！";
            }

            if(accessor.hasAccessed(service))
            {
                return "用户已经购买该服务！";
            }

            if(!service.accessNotFull())
            {
                return "该服务购买已达上限！";
            }

            ServiceAccess serviceAccess = new ServiceAccess();
            serviceAccess.setService(service);
            serviceAccess.setAccessor(accessor);
            serviceAccess.setCreatedAt(LocalDateTime.now());

            serviceAccessRepository.save(serviceAccess);
            return "购买服务成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String unaccessServiceByServiceId(long serviceId, long unaccessorId)
    {
        try
        {
            Service service = serviceRepository.findById(serviceId).orElse(null);
            if(service == null)
            {
                return "服务不存在！";
            }

            User unaccessor = userRepository.findById(unaccessorId).orElse(null);
            if(unaccessor == null)
            {
                return "用户不存在！";
            }

            if(!unaccessor.hasAccessed(service))
            {
                return "用户未购买该服务！";
            }

            serviceAccessRepository.deleteByServiceAndAccessor(service, unaccessor);
            return "取消购买服务成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    @Override
    public String publishService(long userId, String title, String description, long price, List<String> requestImages)
    {
        try
        {
            User publisher = userRepository.findById(userId).orElse(null);
            if(publisher == null)
            {
                return "用户不存在！";
            }

            String imagesURL = ImageUtil.mergeImages(requestImages);

            Service service = new Service();
            service.setTitle(title);
            service.setPrice(price);
            service.setOwner(publisher);
            service.setImages(imagesURL);
            service.setDescription(description);
            service.setCreatedAt(LocalDateTime.now());

            serviceRepository.save(service);

            return "服务发布成功！";
        }
        catch (Exception e)
        {
            throw e;
        }
    }
}
