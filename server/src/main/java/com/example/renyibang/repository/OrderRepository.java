package com.example.renyibang.repository;

import com.example.renyibang.entity.Order;
import com.example.renyibang.entity.User;
import com.example.renyibang.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository<T extends Order<U>, U> extends JpaRepository<T, Long> {
  List<T> findByOwner(User owner);

  List<T> findByOwnerAndType(User owner, Byte type);

  List<T> findByAccessor(User accessor);

  List<T> findByAccessorAndType(User accessor, Byte type);

  List<T> findByStatus(OrderStatus status);

  List<T> findByStatusAndType(OrderStatus status, Byte type);

  boolean existsById(long orderId);

  T findById(long orderId);

  List<T> findAll();

  List<T> findByType(Byte type);

//  T save(T order);

  void deleteById(long orderId);

  void delete(T order);

  void deleteAll();

  long count();
}
