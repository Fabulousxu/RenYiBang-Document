package com.example.renyibang.service;

import com.example.renyibang.entity.Order;
import com.example.renyibang.entity.User;
import com.example.renyibang.enums.OrderStatus;
import java.util.List;
import org.antlr.v4.runtime.misc.Pair;

public interface OrderService<T extends Order<U>, U> {
	List<T> findByOwnerId(long ownerId);

	List<T> findByAccessorId(long accessorId);

	List<T> findByItemId(long itemId);

	List<T> findByStatus(OrderStatus status);

	// 根据订单ID查找订单
	T findById(long TId);

	// 返回所有订单
	List<T> findAllOrders();

	// 生成初始订单
	long createOrder(long taskId, long ownerId, long accessorId, long cost);

	// 标记订单状态
	Pair<Boolean, String> markOrderStatus(long orderId, OrderStatus status);

//	暴力设置订单状态
	boolean setOrderStatusForce(long orderId, OrderStatus status);

	// 校验订单是否存在
	boolean checkOrderExist(long orderId);

	// 校验订单状态是否为指定状态
	boolean checkOrderStatus(long orderId, OrderStatus status);

	void payOrder(T order);

	void completeOrder(T order);

	void confirmOrder(T order);

	void cancelOrder(T order);

	// 用户转账
	void modifyUserBalance(User user, long amount);
}
	
	
	
	