package com.birnbaua.easyshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.repository.OrderRepository;
import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.order.Order;
import com.birnbaua.easyshop.shop.order.id.OrderId;

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
	
	public List<Order> getOpenOrders(String shop) {
		return or.getOpenOrdersOfShop(shop);
	}
	
	public Order deleteById(OrderId id) {
		Order order = or.getOne(id);
		or.deleteById(id);
		return order;
	}
	
	public Order setIsDone(OrderId id) {
		Order order = or.getOne(id);
		order.setIsOpen(false);
		save(order);
		return order;
	}
	
	public Long getLastNr(String shop) {
		Long lastNr= or.getLastNr(shop);
		if(lastNr == null) {
			return 0L;
		}
		return lastNr;
	}

	public Order getOrderById(OrderId orderId) {
		return or.getOne(orderId);
	}

}
