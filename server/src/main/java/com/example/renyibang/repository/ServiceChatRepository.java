package com.example.renyibang.repository;

import com.example.renyibang.entity.ServiceChat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceChatRepository extends JpaRepository<ServiceChat, Long> {
  List<ServiceChat> findByChatter_UserIdOrService_Owner_UserIdOrderByLastTimeDesc(
      long userId1, long userId2);

  ServiceChat findByService_ServiceIdAndChatter_UserId(long id, long userId);
}
