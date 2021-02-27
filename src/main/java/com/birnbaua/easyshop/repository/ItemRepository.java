package com.birnbaua.easyshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.birnbaua.easyshop.shop.item.Item;
import com.birnbaua.easyshop.shop.order.id.ItemId;

public interface ItemRepository extends JpaRepository<Item,ItemId> {
	
	@Query("SELECT i FROM Item i WHERE i.maxAmount > 0")
	public List<Item> findAllAvaliable();
	
	@Query("SELECT i FROM Item i WHERE i.shop.name = ?1")
	public List<Item> findAllOfShop(String shop);
	
	@Query("SELECT i FROM Item i WHERE i.shop.name=?1 AND i.isAvaliable=true")
	public List<Item> findAvaliableItems(String shop);

}
