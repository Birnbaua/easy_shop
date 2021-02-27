package com.birnbaua.easyshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.birnbaua.easyshop.repository.ShopRepository;
import com.birnbaua.easyshop.shop.Shop;

@Service
public class ShopService extends JpaService<Shop,String> {
	
	@Autowired
	private ShopRepository sr;
	
	public Shop save(Shop shop) {
		return sr.save(shop);
	}
	
	public Shop getShopById(String id) {
		return sr.getOne(id);
	}
	
	public List<Shop> getAll() {
		return sr.findAll();
	}
	
	public List<Shop> getAllOfUser(String username) {
		return sr.getAllOfUser(username);
	}
	
	public Shop getShopOfUsername(String username) {
		return sr.shopOfUser(username).orElse(null);
	}
	
	public Integer getMaxOrdersPerTable(String shop, Integer table) {
		return sr.getMaxOrdersPerTable(shop);
	}
	
	public Boolean hasConfig(String shop) {
		return sr.hasConfig(shop);
	}

	@Override
	public JpaRepository<Shop, String> getRepository() {
		return sr;
	}
	
}
