package com.example.renyibang.repository;

import com.example.renyibang.entity.TaskChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskChatMessageRepository extends JpaRepository<TaskChatMessage, Long> {}
