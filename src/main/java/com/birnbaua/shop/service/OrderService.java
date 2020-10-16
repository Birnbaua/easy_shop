package com.birnbaua.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.birnbaua.shop.order.Order;
import com.birnbaua.shop.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository or;
	
	public Order save(Order order) {
		return or.save(order);
	}
	
	public List<Order> getOpenOrders() {
		return or.getOpenOrders();
	}
	
	public Order deleteById(Long id) {
		Order order = or.getOne(id);
		or.deleteById(id);
		return order;
	}
	
	public Order setIsDone(Long id) {
		Order order = or.getOne(id);
		order.setIsOpen(false);
		save(order);
		return order;
	}

}
