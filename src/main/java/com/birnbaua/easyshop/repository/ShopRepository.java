package com.birnbaua.easyshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.birnbaua.easyshop.shop.Shop;

public interface ShopRepository extends JpaRepository<Shop,String> {
	
}
