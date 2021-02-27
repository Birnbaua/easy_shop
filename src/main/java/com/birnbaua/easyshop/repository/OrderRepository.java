package com.birnbaua.easyshop.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.birnbaua.easyshop.shop.ShopTable;
import com.birnbaua.easyshop.shop.order.Order;
import com.birnbaua.easyshop.shop.order.id.OrderId;
import com.birnbaua.easyshop.shop.order.id.ShopTableId;

public interface OrderRepository extends JpaRepository<Order,OrderId> {
	
	@Query("SELECT o FROM Order o WHERE o.isOpen=true")
	public List<Order> getOpenOrders();

	@Query("SELECT o FROM Order o WHERE o.table.shop=?1 AND o.isOpen = true")
	public List<Order> findOpenOrders(String shop);
	
	@Query("SELECT o FROM Order o WHERE o.table.shop=?1 AND o.isOpen = true")
	public List<Order> findOpenOrders(String shop, Pageable pageable);
	
	@Query("SELECT o FROM Order o WHERE o.table.shop=?1 AND o.updatedAt < ?2 AND o.isOpen = true")
	public List<Order> findOpenOrders(String shop, Timestamp from, Pageable pageable);
	
	@Query("SELECT o FROM Order o WHERE o.table=?1 AND o.isOpen = true")
	public List<Order> findOpenOrders(ShopTable table);
	
	@Query("SELECT o FROM Order o WHERE o.table=?1 AND o.isOpen = true ORDER BY o.updatedAt ASC")
	public List<Order> findOpenOrders(ShopTable table, Pageable pageable);
	
	@Query("SELECT o FROM Order o WHERE o.table.shop=?1 AND o.updatedAt < ?2 AND o.isOpen = true")
	public List<Order> findOpenOrders(ShopTable table, Timestamp from, Pageable pageable);
	
	@Query("SELECT MAX(o.nr) FROM Order o WHERE o.table.shop = ?1")
	public Long getLastNr(String shop);
	
	@Query("SELECT COUNT(o) FROM Order o WHERE o.isOpen=true AND o.table.shop=?1")
	public Long countOpenOrders(String shop);
	
	@Query("SELECT COUNT(o) FROM Order o WHERE o.isOpen=true AND o.table=?1")
	public long countOpenOrders(ShopTable shopTable);


}
