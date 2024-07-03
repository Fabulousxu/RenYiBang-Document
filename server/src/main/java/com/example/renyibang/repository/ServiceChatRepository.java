package com.example.renyibang.repository;

import com.example.renyibang.entity.ServiceChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceChatRepository extends JpaRepository<ServiceChat, Long> {}
