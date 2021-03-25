package com.birnbaua.easyshop.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.repository.OrderPosRepository;
import com.birnbaua.easyshop.repository.OrderRepository;
import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.ShopTable;
import com.birnbaua.easyshop.shop.order.Order;
import com.birnbaua.easyshop.shop.order.id.ItemId;
import com.birnbaua.easyshop.shop.order.id.OrderId;

@Service
public class OrderService extends JpaService<Order,OrderId>{
	
	@Autowired
	private OrderRepository or;
	
	@Autowired
	private OrderPosRepository opr;
	
	@Autowired
	private ItemService is;
	
	@Autowired
	private ShopService ss;
	
	@Override
	public Order save(Order order) {
		order.getOrderPos().forEach(x -> {
			x.setItem(is.findById(new ItemId(order.getTable().getShop().getName(), x.getItem().getName())));
		});
		order.setPrice(order.getOrderPos().stream().mapToDouble(x -> x.getAmount() * x.getItem().getPrice()).sum());
		return or.save(order);
	}
	
	public void checkOrderCount(String shop, Integer table) throws Exception {
		if(ss.hasConfig(shop)) {
			if(countOpenOrders(shop,table) >= ss.getMaxOrdersPerTable(shop, table)) {
				throw new Exception("Maximum orders per table reached");
			}
		}
	}
	
	
	
	public long countOpenOrders(String shop) {
		return or.countOpenOrders(shop);
	}
	
	public long countOpenOrders(String shop, Integer table) {
		return or.countOpenOrders(new ShopTable(new Shop(shop),table));
	}
	
	public List<Order> findOpenOrders(String shop, Pageable page) {
		return or.findOpenOrders(shop,page).toList();
	}
	
	public List<Order> findOpenOrders(String shop, Timestamp time, Pageable page) {
		return or.findOpenOrders(shop, time, page).toList();
	}
	
	public List<Order> findOpenOrders(String shop, Integer table, Pageable page) {
		return or.findOpenOrders(new ShopTable(new Shop(shop),table),page).toList();
	}
	
	public List<Order> findOpenOrders(String shop, Integer table, Timestamp time, Pageable page) {
		return or.findOpenOrders(new ShopTable(new Shop(shop),table), time, page).toList();
	}
	
	public List<Order> findAll(Pageable page) {
		return or.findAll(page).toList();
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

	public void deleteInBatch(String shop, int size) {
		Page<Order> page = null;
		int nr = 0;
		do {
			page = or.findOrders(shop,PageRequest.of(nr++, size));
			opr.deleteInBatch(page.stream().flatMap(x -> x.getOrderPos().stream()).collect(Collectors.toList()));
			this.deleteInBatch(page.toList());
		}while(page.isLast() == false);
	}

}
