package com.example.renyibang.dao;

import com.example.renyibang.entity.Order;
import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.TaskOrder;
import com.example.renyibang.entity.User;
import com.example.renyibang.enums.OrderStatus;

import java.util.List;

public interface OrderDao<T extends Order<U>, U>{
	List<T> findByOwner(User owner);

	List<T> findByOwnerAndType(User owner, Byte type);

	List<T> findByAccessor(User accessor);

	List<T> findByAccessorAndType(User accessor, Byte type);

	List<T> findByItem(U item);

	List<T> findByStatus(OrderStatus status);

	List<T> findByStatusAndType(OrderStatus status, Byte type);

	T findById(long orderId);

	List<T> findAllOrders();

	List<T> findByType(Byte type);

	T save(T order);

	boolean existsById(long orderId);
}
