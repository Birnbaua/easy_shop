package com.birnbaua.easyshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.ShopTable;

public interface ShopRepository extends JpaRepository<Shop,String> {
	
	@Query("SELECT s FROM Shop s WHERE s.owner.username = ?1")
	public Optional<Shop> shopOfUser(String username);
	
	@Query("SELECT s FROM Shop s WHERE s.owner.username = ?1")
	public List<Shop> getAllOfUser(String username);
	
	@Query("SELECT s.config.maxNrOfOrdersPerTable FROM Shop s WHERE s.name=?1")
	public Integer getMaxOrdersPerTable(String shop);
	
	@Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Shop s WHERE s.name=?1 AND s.config IS NOT NULL")
	public Boolean hasConfig(String shop);
}
