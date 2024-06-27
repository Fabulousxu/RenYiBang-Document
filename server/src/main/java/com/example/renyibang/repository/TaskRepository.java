package com.example.renyibang.repository;

import com.example.renyibang.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Override
    Page<Task> findAll(Pageable pageable);

    @Query("SELECT t FROM Task t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(t.owner.nickname) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Task> findByTitleOrDescriptionOrOwnerNicknameContainingIgnoreCase(@Param("searchText") String searchText, Pageable pageable);

    List<Task> findByOwnerId(long ownerId);

    List<Task> findByStatus(TaskStatus status);

    Task findTaskById(long taskId);

    List<Task> findAllTasks();

    long createTask(long ownerId, String title, String description, long reward);

    boolean markTaskStatus(long taskId, TaskStatus status);

    boolean checkTaskExist(long taskId);

    boolean checkTaskStatus(long taskId, TaskStatus status);

}
