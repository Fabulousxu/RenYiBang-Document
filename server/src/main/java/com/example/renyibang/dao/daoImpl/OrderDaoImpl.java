package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.OrderDao;
import com.example.renyibang.entity.Order;
import com.example.renyibang.entity.Task;
import com.example.renyibang.entity.User;
import com.example.renyibang.enums.OrderStatus;
import com.example.renyibang.repository.OrderRepository;
import com.example.renyibang.repository.ServiceOrderRepository;
import com.example.renyibang.repository.TaskOrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl<T extends Order<U>, U> implements OrderDao<T, U> {
	@Autowired
	private TaskOrderRepository taskOrderRepository;
	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	@Autowired
	private OrderRepository<T, U> orderRepository;

	@Override
	public List<T> findByOwner(User owner) {
			return orderRepository.findByOwner(owner);
	}

	@Override
	public List<T> findByOwnerAndType(User owner, Byte type) {
			return orderRepository.findByOwnerAndType(owner, type);
	}

	@Override
	public List<T> findByAccessor(User accessor) {
			return orderRepository.findByAccessor(accessor);
	}

	@Override
	public List<T> findByAccessorAndType(User accessor, Byte type) {
			return orderRepository.findByAccessorAndType(accessor, type);
	}

	@Override
	public List<T> findByItem(U item) {
			if(item instanceof Task) {
				return (List<T>) taskOrderRepository.findByItem((Task) item);
			} else {
				return (List<T>) serviceOrderRepository.findByItem((com.example.renyibang.entity.Service) item);
			}
	}

	@Override
	public List<T> findByStatus(OrderStatus status) {
			return orderRepository.findByStatus(status);
	}

	@Override
	public List<T> findByStatusAndType(OrderStatus status, Byte type) {
			return orderRepository.findByStatusAndType(status, type);
	}

	@Override
	public T findById(long orderId) {
			return orderRepository.findById(orderId);
	}

	@Override
	public List<T> findAllOrders() {
			return orderRepository.findAll();
	}

	@Override
	public List<T> findByType(Byte type) {
			return orderRepository.findByType(type);
	}

	@Override
	public T save(T order) {
			return orderRepository.save(order);
	}

	@Override
	public boolean existsById(long orderId) {
			return orderRepository.existsById(orderId);
	}
}