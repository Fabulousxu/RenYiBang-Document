package com.example.renyibang.dao;

import com.example.renyibang.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ServiceDao {
    Service findById(long serviceId);
    Page<Service> searchServiceByPaging(String keyword, Pageable pageable, LocalDateTime beginDateTime, LocalDateTime endDateTime, long priceLow, long priceHigh);

    String collectServiceByServiceId(long serviceId, long collectorId);

    String uncollectServiceByServiceId(long serviceId, long uncollectorId);

    String accessServiceByServiceId(long serviceId, long accessorId);

    String unaccessServiceByServiceId(long serviceId, long unaccessorId);
}
