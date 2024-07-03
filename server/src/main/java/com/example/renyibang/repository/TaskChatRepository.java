package com.example.renyibang.repository;

import com.example.renyibang.entity.TaskChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskChatRepository extends JpaRepository<TaskChat, Long> {}
