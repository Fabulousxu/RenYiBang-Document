package com.example.renyibang.repository;

import com.example.renyibang.entity.TaskChat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskChatRepository extends JpaRepository<TaskChat, Long> {
  List<TaskChat> findByChatter_UserIdOrTask_Owner_UserIdOrderByLastTimeDesc(
      long userId1, long userId2);

  TaskChat findByTask_TaskIdAndChatter_UserId(long taskId, long userId);
}
