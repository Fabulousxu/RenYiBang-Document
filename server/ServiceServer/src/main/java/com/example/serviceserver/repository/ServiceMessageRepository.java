package com.example.serviceserver.repository;

import com.example.serviceserver.entity.ServiceMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceMessageRepository extends JpaRepository<ServiceMessage, Long> {
    Page<ServiceMessage> findByServiceServiceId(long serviceId, Pageable pageable);

}
