package com.birnbaua.shop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.birnbaua.shop.order.Item;
import com.birnbaua.shop.repository.ItemRepository;

@Service
public class ItemService {
	
	@Autowired
	private ItemRepository ir;
	
	public Item save(Item item) {
		return ir.save(item);
	}
	
	public List<Item> getAll() {
		return ir.findAll();
	}
	
	public List<Item> getAllAvaliable() {
		return ir.findAllAvaliable();
	}
	
	public Item deleteById(String id) {
		Item item = null;
		item = ir.getOne(id);
		ir.deleteById(id);
		return item;
	}

}
