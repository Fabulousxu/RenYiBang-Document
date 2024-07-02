package com.example.renyibang.repository;

import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskOrder;

public interface TaskOrderRepository extends OrderRepository<TaskOrder, Task> {
	// findByTask method
	TaskOrder findByItem(Task task);
}