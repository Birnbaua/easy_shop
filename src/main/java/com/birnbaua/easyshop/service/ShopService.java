package com.birnbaua.easyshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.repository.ItemRepository;
import com.birnbaua.easyshop.repository.OrderRepository;
import com.birnbaua.easyshop.repository.ShopRepository;
import com.birnbaua.easyshop.shop.Shop;

@Service
public class ShopService {
	
	@Autowired
	private ShopRepository sr;
	
	@Autowired
	private ItemRepository is;
	
	@Autowired
	private OrderRepository os;
	
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
		os.deleteInBatch(shop.getOrders());
		is.deleteInBatch(shop.getItems());
		sr.deleteById(id);
		return shop;
	}
	
	public List<Shop> getAllOfUser(String username) {
		return sr.getAllOfUser(username);
	}
	
	public Shop getShopOfUsername(String username) {
		return sr.shopOfUser(username).orElse(null);
	}
	
}
