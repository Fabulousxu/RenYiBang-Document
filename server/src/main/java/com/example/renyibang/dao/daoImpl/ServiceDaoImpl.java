package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.ServiceDao;
import com.example.renyibang.entity.Service;
import com.example.renyibang.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ServiceDaoImpl implements ServiceDao {
    @Autowired
    ServiceRepository serviceRepository;

    @Override
    public List<Service> searchServiceByPaging(String keyword, Pageable pageable, LocalDateTime beginDateTime, LocalDateTime endDateTime, long priceLow, long priceHigh)
    {
        if(!keyword.isEmpty())
        {
            return serviceRepository.searchServices(keyword, priceLow, priceHigh, beginDateTime, endDateTime, pageable).getContent();
        }
        else
        {
            return serviceRepository.findByPriceBetweenAndCreatedAtBetween(priceLow, priceHigh, beginDateTime, endDateTime, pageable).getContent();
        }
    }

    @Override
    public Service findById(long serviceId) {
        return serviceRepository.findById(serviceId).orElse(null);
    }
}
