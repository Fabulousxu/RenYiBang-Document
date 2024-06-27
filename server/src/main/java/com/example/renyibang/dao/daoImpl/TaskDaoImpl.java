package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.TaskDao;
import com.example.renyibang.entity.Task;
import com.example.renyibang.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao{
    @Autowired
    TaskRepository taskRepository;

    @Override
    public List<Task> searchTaskByPaging(String keyword, Pageable pageable)
    {
        if(!keyword.isEmpty())
        {
            return taskRepository.findByTitleOrDescriptionOrOwnerNicknameContainingIgnoreCase(keyword, pageable);
        }

        else
        {
            return taskRepository.findAll(pageable).getContent();
        }
    }
}
