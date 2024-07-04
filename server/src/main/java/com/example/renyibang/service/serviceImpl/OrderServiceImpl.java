//package com.example.renyibang.service.serviceImpl;
//
//import com.example.renyibang.dao.OrderDao;
//import com.example.renyibang.dao.ServiceDao;
//import com.example.renyibang.dao.TaskDao;
//import com.example.renyibang.dao.UserDao;
//import com.example.renyibang.entity.Service;
//import com.example.renyibang.entity.Task;
//import com.example.renyibang.entity.Order;
//import com.example.renyibang.entity.TaskOrder;
//import com.example.renyibang.entity.User;
//import com.example.renyibang.enums.OrderStatus;
//import com.example.renyibang.service.OrderService;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//@org.springframework.stereotype.Service
//public class OrderServiceImpl<T extends Order<U>, U> implements OrderService<T, U> {
//	private final Class<T> type;
//
//	@Autowired private OrderDao<T, U> orderDao;
//	@Autowired private TaskDao taskDao;
//	@Autowired private ServiceDao serviceDao;
//	@Autowired private UserDao userDao;
//
//	public OrderServiceImpl(Class<T> type) {
//		this.type = type;
//	}
//
//	@Override
//	public List<T> findByOwnerId(long ownerId) {
//		User owner = userDao.findById(ownerId)
//						.orElseThrow(() -> new EntityNotFoundException("User not found with id " + ownerId));
//		return orderDao.findByOwner(owner);
//	}
//
//	@Override
//	public List<T> findByOwnerIdAndType(long ownerId, byte type) {
//		User owner = userDao.findById(ownerId)
//						.orElseThrow(() -> new EntityNotFoundException("User not found with id " + ownerId));
//		return orderDao.findByOwnerAndType(owner, type);
//	}
//
//	@Override
//	public List<T> findByAccessorId(long accessorId) {
//		User accessor = userDao.findById(accessorId)
//						.orElseThrow(() -> new EntityNotFoundException("User not found with id " + accessorId));
//		return orderDao.findByAccessor(accessor);
//	}
//
//	@Override
//	public List<T> findByAccessorIdAndType(long accessorId, byte type) {
//		User accessor = userDao.findById(accessorId)
//						.orElseThrow(() -> new EntityNotFoundException("User not found with id " + accessorId));
//		return orderDao.findByAccessorAndType(accessor, type);
//	}
//
//	@Override
//	public List<T> findByItemId(long taskId) {
//		// 如果T是TaskOrder，
//		// 则taskDao.findById(taskId)返回Task对象
//		// 如果T是ServiceOrder，
//		// 则taskDao.findById(taskId)返回Service对象
//		if(type == TaskOrder.class) {
//			Task task = taskDao.findById(taskId);
//			if(task == null) {
//				throw new EntityNotFoundException("Task not found with id " + taskId);
//			}
//			return orderDao.findByItem((U) task);
//		} else {
//			Service service = serviceDao.findById(taskId);
//			if(service == null) {
//				throw new EntityNotFoundException("Service not found with id " + taskId);
//			}
//			return orderDao.findByItem((U) service);
//
//		}
//	}
//
//	@Override
//	public List<T> findByStatus(OrderStatus status) {
//		return orderDao.findByStatus(status);
//	}
//
//	@Override
//	public List<T> findByStatusAndType(OrderStatus status, byte type) {
//		return orderDao.findByStatusAndType(status, type);
//	}
//
//	@Override
//	public T findById(long orderId) {
//		return orderDao.findById(orderId);
//	}
//
//	@Override
//	public List<T> findAllOrders() {
//		return orderDao.findAllOrders();
//	}
//
//	@Override
//	public long createOrder(long taskId, long ownerId, long accessorId, long cost) {
//		// Fetch the Task or Service entity
//		U item = null;
//		if(type == TaskOrder.class) {
//			Task task = taskDao.findById(taskId);
//			if(task == null) {
//				throw new EntityNotFoundException("Task not found with id " + taskId);
//			}
//			item = (U) task;
//		} else {
//			Service service = serviceDao.findById(taskId);
//			if(service == null) {
//				throw new EntityNotFoundException("Service not found with id " + taskId);
//			}
//			item = (U) service;
//		}
//
//		// Fetch the Owner (User) entity
//		User owner = userDao.findById(ownerId)
//						.orElseThrow(() -> new EntityNotFoundException("User not found with id " + ownerId));
//
//		// Fetch the Accessor (User) entity
//		User accessor = userDao.findById(accessorId)
//						.orElseThrow(() -> new EntityNotFoundException("User not found with id " + accessorId));
//
//		// Create a new Order
//		T order = null;
//		try {
//			order = type.newInstance();
//		} catch (InstantiationException | IllegalAccessException e) {
//			e.printStackTrace();
//		}
//
//		order.setItem(item);
//		order.setOwner(owner);
//		order.setAccessor(accessor);
//		order.setCost(cost);
//		return orderDao.save(order).getOrderId();
//	}
//
//	@Override
//	public boolean markOrderStatus(long orderId, OrderStatus status) {
//		T order = orderDao.findById(orderId);
//		if (order == null) {
//			throw new EntityNotFoundException("Order not found with id " + orderId);
//		}
//		order.setStatus(status);
//		orderDao.save(order);
//		return true;
//	}
//
//	@Override
//	public boolean checkOrderExist(long orderId) {
//		return orderDao.existsById(orderId);
//	}
//
//	@Override
//	public boolean checkOrderStatus(long orderId, OrderStatus status) {
//		T order = orderDao.findById(orderId);
//		if (order == null) {
//			throw new EntityNotFoundException("Order not found with id " + orderId);
//		}
//		return order.getStatus() == status;
//	}
//
//	@Override
//	public void payOrder(T order) {
//		order.setStatus(OrderStatus.IN_PROGRESS);
//		orderDao.save(order);
//	}
//
//	@Override
//	public void modifyUserBalance(User user, long amount) {
//		if(user == null) {
//			throw new EntityNotFoundException("User not found");
//		}
//		user.setBalance(user.getBalance() + amount);
//		userDao.save(user);
//	}
//}