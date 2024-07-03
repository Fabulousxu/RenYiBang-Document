package com.example.renyibang.repository;

import com.example.renyibang.entity.ServiceComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceCommentRepository extends JpaRepository<ServiceComment, Long> {
    Page<ServiceComment> findByServiceServiceId(long serviceId, Pageable pageable);
}
