package com.example.renyibang.service.serviceImpl;

import com.example.renyibang.dao.OrderDao;
import com.example.renyibang.dao.ServiceDao;
import com.example.renyibang.dao.UserDao;
import com.example.renyibang.entity.Service;
import com.example.renyibang.entity.ServiceOrder;
import com.example.renyibang.entity.User;
import com.example.renyibang.enums.OrderStatus;
import com.example.renyibang.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceOrderServiceImpl implements OrderService<ServiceOrder, Service> {
	@Autowired
	private OrderDao<ServiceOrder, Service> serviceOrderDao;
	@Autowired private ServiceDao serviceDao;
	@Autowired private UserDao userDao;

	@Override
	public List<ServiceOrder> findByOwnerId(long ownerId) {
		User owner = userDao.findById(ownerId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id " + ownerId));
		return serviceOrderDao.findByOwnerAndType(owner, (byte) 1);
	}

	@Override
	public List<ServiceOrder> findByAccessorId(long accessorId) {
		User accessor = userDao.findById(accessorId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id " + accessorId));
		return serviceOrderDao.findByAccessorAndType(accessor, (byte) 1);
	}

	@Override
	public List<ServiceOrder> findByItemId(long serviceId) {
		Service service = serviceDao.findById(serviceId);
		if(service == null) {
			throw new EntityNotFoundException("service not found with id " + serviceId);
		}
		return serviceOrderDao.findByItem(service);
	}

	@Override
	public List<ServiceOrder> findByStatus(OrderStatus status) {
		return serviceOrderDao.findByStatusAndType(status, (byte) 1);
	}

	@Override
	public ServiceOrder findById(long ServiceOrderId) {
		return serviceOrderDao.findById(ServiceOrderId);
	}

	@Override
	public List<ServiceOrder> findAllOrders() {
		return serviceOrderDao.findByType((byte) 1);
//		return serviceOrderDao.findAllOrders();
	}

	@Override
	public long createOrder(long serviceId, long ownerId, long accessorId, long cost) {
		// Fetch the service entity
		Service service = serviceDao.findById(serviceId);
		if(service == null) {
			throw new EntityNotFoundException("service not found with id " + serviceId);
		}

		// Fetch the Owner (User) entity
		User owner = userDao.findById(ownerId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id " + ownerId));

		// Fetch the Accessor (User) entity
		User accessor = userDao.findById(accessorId)
				.orElseThrow(() -> new EntityNotFoundException("User not found with id " + accessorId));


		// Create a new ServiceOrder
		ServiceOrder ServiceOrder = new ServiceOrder();
		ServiceOrder.setItem(service);
		ServiceOrder.setOwner(owner);
		ServiceOrder.setAccessor(accessor);
		ServiceOrder.setCost(cost);
		ServiceOrder.setStatus(OrderStatus.UNPAID);

		// Save the ServiceOrder and return its ID
		return serviceOrderDao.save(ServiceOrder).getOrderId();
	}


	@Override
	public Pair<Boolean, String> markOrderStatus(long ServiceOrderId, OrderStatus status) {
		ServiceOrder serviceOrder = serviceOrderDao.findById(ServiceOrderId);
		if (serviceOrder == null) {
			return new Pair<>(false, "订单不存在");
		}

		OrderStatus currentStatus = serviceOrder.getStatus();
		if (currentStatus == OrderStatus.UNPAID && status == OrderStatus.IN_PROGRESS) {
			this.payOrder(serviceOrder);
			return new Pair<>(true, "订单支付成功");
		} else if (currentStatus == OrderStatus.IN_PROGRESS && status == OrderStatus.COMPLETED) {
			this.completeOrder(serviceOrder);
			return new Pair<>(true, "订单完成成功");
		} else if (currentStatus == OrderStatus.COMPLETED && status == OrderStatus.CONFIRMED) {
			this.confirmOrder(serviceOrder);
			return new Pair<>(true, "订单确认成功");
		} else if(status == OrderStatus.CANCELLED && currentStatus != OrderStatus.CONFIRMED && currentStatus != OrderStatus.CANCELLED) {
			this.cancelOrder(serviceOrder);
			return new Pair<>(true, "订单取消成功");
		} else {
			return new Pair<>(false, "订单状态不合法");
		}
	}

	@Override
	public boolean setOrderStatusForce(long orderId, OrderStatus status) {
		ServiceOrder ServiceOrder = serviceOrderDao.findById(orderId);
		if (ServiceOrder == null) {
			return false;
		}
		ServiceOrder.setStatus(status);
		serviceOrderDao.save(ServiceOrder);
		return true;
	}

	@Override
	public boolean checkOrderExist(long ServiceOrderId) {
		return serviceOrderDao.existsById(ServiceOrderId);
	}

	@Override
	public boolean checkOrderStatus(long ServiceOrderId, OrderStatus status) {
		return serviceOrderDao.findById(ServiceOrderId).getStatus() == status;
	}

	@Override
	public void payOrder(ServiceOrder ServiceOrder) {
		// 获得任务发布者
		User owner = ServiceOrder.getOwner();
		this.modifyUserBalance(owner, -ServiceOrder.getCost());

		// 修改订单状态
		ServiceOrder.setStatus(OrderStatus.IN_PROGRESS);
		serviceOrderDao.save(ServiceOrder);
	}

	@Override
	public void completeOrder(ServiceOrder order) {
		order.setStatus(OrderStatus.COMPLETED);
		serviceOrderDao.save(order);
	}

	@Override
	public void confirmOrder(ServiceOrder order) {
		order.setStatus(OrderStatus.CONFIRMED);
		// 转账
		this.modifyUserBalance(order.getAccessor(), order.getCost());
		serviceOrderDao.save(order);
	}

	@Override
	public void cancelOrder(ServiceOrder order) {
		order.setStatus(OrderStatus.CANCELLED);
		// 退款
		this.modifyUserBalance(order.getOwner(), order.getCost());
		serviceOrderDao.save(order);
	}

	@Override
	public void modifyUserBalance(User user, long amount) {
		if(user == null) {
			throw new EntityNotFoundException("User not found");
		}
		user.setBalance(user.getBalance() + amount);
		userDao.save(user);
	}
}

