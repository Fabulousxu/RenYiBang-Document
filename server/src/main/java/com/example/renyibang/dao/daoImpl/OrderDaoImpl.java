package com.example.renyibang.dao.daoImpl;

import com.example.renyibang.dao.OrderDao;
import com.example.renyibang.entity.Order;
import com.example.renyibang.entity.User;
import com.example.renyibang.enums.OrderStatus;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl<T extends Order<U>, U> implements OrderDao<T, U> {
			@Override
		public List<T> findByOwner(User owner) {
				return null;
		}

		@Override
		public List<T> findByAccessor(User accessor) {
				return null;
		}

		@Override
		public List<T> findByItem(U item) {
				return null;
		}

		@Override
		public List<T> findByStatus(OrderStatus status) {
				return null;
		}

		@Override
		public T findById(long orderId) {
				return null;
		}

		@Override
		public List<T> findAllOrders() {
				return null;
		}

		@Override
		public T save(T order) {
				return null;
		}

		@Override
		public boolean existsById(long orderId) {
				return false;
		}
}