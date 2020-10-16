package com.birnbaua.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.birnbaua.shop.order.Item;

public interface ItemRepository extends JpaRepository<Item,String> {
	
	@Query("SELECT i FROM Item i WHERE i.maxAmount > 0")
	public List<Item> findAllAvaliable();

}
