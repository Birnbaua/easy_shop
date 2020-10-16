package com.birnbaua.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.birnbaua.shop.order.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {
	
	@Query("SELECT o FROM Order o WHERE o.isOpen=true")
	public List<Order> getOpenOrders();

}
