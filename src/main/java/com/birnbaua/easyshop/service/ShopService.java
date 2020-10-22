package com.birnbaua.easyshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.repository.ShopRepository;
import com.birnbaua.easyshop.shop.Shop;

@Service
public class ShopService {
	
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
	
	public Shop deleteById(String id) {
		Shop shop = null;
		shop = getShopById(id);
		sr.deleteById(id);
		return shop;
	}
	
}
