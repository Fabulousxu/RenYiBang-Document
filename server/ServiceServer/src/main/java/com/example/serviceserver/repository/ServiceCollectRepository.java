package com.example.serviceserver.repository;

import com.example.serviceserver.entity.Service;
import com.example.serviceserver.entity.ServiceCollect;
import com.example.serviceserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCollectRepository extends JpaRepository<ServiceCollect, Long> {
    void deleteByServiceAndCollector(Service service, User uncollector);
}
