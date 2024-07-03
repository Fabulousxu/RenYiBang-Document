package com.example.renyibang.repository;

import com.example.renyibang.entity.Service;
import com.example.renyibang.entity.ServiceAccess;
import com.example.renyibang.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceAccessRepository extends JpaRepository<ServiceAccess, Long> {
    void deleteByServiceAndAccessor(Service service, User unaccessor);
}
