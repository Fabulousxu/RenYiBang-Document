package com.example.renyibang.repository;

import com.example.renyibang.entity.ServiceChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceChatMessageRepository extends JpaRepository<ServiceChatMessage, Long> {
  Page<ServiceChatMessage>
      findByServiceChat_ServiceChatIdAndServiceChatMessageIdLessThanOrderByCreatedAtDesc(
          long chatId, long lastMessageId, PageRequest of);
}
