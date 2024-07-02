package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.TaskDao;
import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskCollect;
import com.example.renyibang.entity.User;
import com.example.renyibang.repository.TaskCollectRepository;
import com.example.renyibang.repository.TaskRepository;
import com.example.renyibang.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao {
  @Autowired TaskRepository taskRepository;

  @Autowired UserRepository userRepository;

  @Autowired TaskCollectRepository taskCollectRepository;

  @Override
  public Task findById(long taskId) {
    return taskRepository.findById(taskId).orElse(null);
  }

  @Override
  public Page<Task> searchTaskByPaging(String keyword, Pageable pageable, LocalDateTime beginDateTime, LocalDateTime endDateTime, long priceLow, long priceHigh)
  {
      if(!keyword.isEmpty())
      {
          return taskRepository.searchTasks(keyword, priceLow, priceHigh, beginDateTime, endDateTime, pageable);
      }
      else
      {
          return taskRepository.findByPriceBetweenAndCreatedAtBetween(priceLow, priceHigh, beginDateTime, endDateTime, pageable);
      }
  }

  @Override
  public Task getTask(long taskId)
  {
      return taskRepository.findById(taskId).orElse(null);
  }

  @Override
  public String collectTaskByTaskId(long taskId, long collectorId)
  {
      try{
          User collector = userRepository.findById(collectorId).orElse(null);
          if(collector == null)
          {
              return "用户不存在！";
          }

          Task task = taskRepository.findById(taskId).orElse(null);
          if(task == null)
          {
              return "任务不存在！";
          }

          if(collector.hasCollected(task))
          {
              return "用户已收藏该任务！";
          }

          task.setCollectedNumber(task.getCollectedNumber() + 1);
          TaskCollect taskCollect = new TaskCollect();
          taskCollect.setTask(task);
          taskCollect.setCollector(collector);
          LocalDateTime now = LocalDateTime.now();
          taskCollect.setCreatedAt(now);

          taskCollectRepository.save(taskCollect);

          return "收藏成功！";
      }
      catch (Exception e)
      {
          throw e;
      }
  }

  @Override
  public String uncollectTaskByTaskId(long taskId, long uncollectorId)
  {
      try
      {
          User uncollector = userRepository.findById(uncollectorId).orElse(null);
          if(uncollector == null)
          {
              return "用户不存在！";
          }

          Task task = taskRepository.findById(taskId).orElse(null);
          if(task == null)
          {
              return "任务不存在！";
          }

          if(!uncollector.hasCollected(task))
          {
              return "用户未收藏该任务！";
          }

          task.setCollectedNumber(task.getCollectedNumber() - 1);
          taskCollectRepository.deleteByTaskAndCollector(task, uncollector);

          return "取消收藏成功！";
      }
      catch (Exception e)
      {
          throw e;
      }
  }
}
