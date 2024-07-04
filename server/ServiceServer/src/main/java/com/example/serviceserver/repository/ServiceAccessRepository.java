package com.example.serviceserver.repository;

import com.example.serviceserver.entity.Service;
import com.example.serviceserver.entity.ServiceAccess;
import com.example.serviceserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceAccessRepository extends JpaRepository<ServiceAccess, Long> {
    void deleteByServiceAndAccessor(Service service, User unaccessor);
}
