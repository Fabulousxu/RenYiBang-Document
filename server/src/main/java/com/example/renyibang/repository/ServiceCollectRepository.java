package com.example.renyibang.repository;

import com.example.renyibang.entity.Service;
import com.example.renyibang.entity.ServiceCollect;
import com.example.renyibang.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCollectRepository extends JpaRepository<ServiceCollect, Long> {
    void deleteByServiceAndCollector(Service service, User uncollector);
}
