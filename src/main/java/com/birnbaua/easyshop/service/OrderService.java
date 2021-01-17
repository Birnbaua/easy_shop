package com.birnbaua.easyshop.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.repository.OrderRepository;
import com.birnbaua.easyshop.shop.order.Order;
import com.birnbaua.easyshop.shop.order.id.ItemId;
import com.birnbaua.easyshop.shop.order.id.OrderId;

@Service
public class OrderService extends JpaService<Order,OrderId>{
	
	@Autowired
	private OrderRepository or;
	
	@Autowired
	private ItemService is;
	
	@Override
	public Order save(Order order) {
		order.getOrderPos().forEach(x -> {
			x.setItem(is.findById(new ItemId(order.getShop().getName(), x.getItem().getName())));
		});
		return or.save(order);
	}
	
	public List<Order> findOpenOrders(String shop, Long time) {
		return or.findOpenOrders(shop, new Timestamp(time));
	}
	
	public List<Order> getOpenOrders() {
		return or.getOpenOrders();
	}
	
	public List<Order> getOpenOrders(String shop) {
		return or.findOpenOrders(shop);
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

	@Override
	public JpaRepository<Order, OrderId> getRepository() {
		return or;
	}

}
