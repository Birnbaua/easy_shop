package com.birnbaua.easyshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.birnbaua.easyshop.shop.Shop;

public interface ShopRepository extends JpaRepository<Shop,String> {
	
	@Query("SELECT s FROM Shop s WHERE s.owner.username = ?1")
	public Optional<Shop> shopOfUser(String username);
	
	@Query("SELECT s FROM Shop s WHERE s.owner.username = ?1")
	public List<Shop> getAllOfUser(String username);
}
