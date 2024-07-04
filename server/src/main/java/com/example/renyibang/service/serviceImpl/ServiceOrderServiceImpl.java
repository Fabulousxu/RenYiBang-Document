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
	public boolean markOrderStatus(long ServiceOrderId, OrderStatus status) {
		ServiceOrder ServiceOrder = serviceOrderDao.findById(ServiceOrderId);
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
	public void modifyUserBalance(User user, long amount) {
		if(user == null) {
			throw new EntityNotFoundException("User not found");
		}
		user.setBalance(user.getBalance() + amount);
		userDao.save(user);
	}
}

		