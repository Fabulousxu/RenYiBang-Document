package com.example.renyibang.repository;

import com.example.renyibang.entity.Service;
import com.example.renyibang.entity.ServiceOrder;

public interface ServiceOrderRepository extends OrderRepository<ServiceOrder, Service> {
	// findByService method
	ServiceOrder findByItem(Service service);
}
