package com.birnbaua.easyshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.birnbaua.easyshop.shop.ShopTable;
import com.birnbaua.easyshop.shop.order.id.ShopTableId;

public interface ShopTableRepository extends JpaRepository<ShopTable,ShopTableId> {
	
	@Query("SELECT t FROM ShopTable t WHERE t.shop.name=?1")
	public List<ShopTable> findTables(String shop);
}
