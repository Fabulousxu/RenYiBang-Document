package com.example.renyibang.dao;

import com.example.renyibang.entity.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ServiceDao {
    List<Service> searchServiceByPaging(String keyword, Pageable pageable, LocalDateTime beginDateTime, LocalDateTime endDateTime, long priceLow, long priceHigh);

	Service findById(long serviceId);
}
