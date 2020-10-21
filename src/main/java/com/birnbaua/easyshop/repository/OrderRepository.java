package com.birnbaua.easyshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.order.Order;
import com.birnbaua.easyshop.shop.order.id.OrderId;

public interface OrderRepository extends JpaRepository<Order,OrderId> {
	
	@Query("SELECT o FROM Order o WHERE o.isOpen=true")
	public List<Order> getOpenOrders();
	
	@Query("SELECT o FROM Order o WHERE o.shop.name = ?1 AND o.isOpen = true")
	public List<Order> getOpenOrdersOfShop(String shop);
	
	@Query("SELECT MAX(o.nr) FROM Order o WHERE o.shop.name = ?1")
	public Long getLastNr(String shop);

}
