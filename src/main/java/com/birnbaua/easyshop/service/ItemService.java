package com.birnbaua.easyshop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.birnbaua.easyshop.repository.ItemRepository;
import com.birnbaua.easyshop.shop.order.Item;
import com.birnbaua.easyshop.shop.order.id.ItemId;

@Service
public class ItemService extends JpaService<Item,ItemId> {
	
	@Autowired
	private ItemRepository ir;
	
	
	public List<Item> getAll() {
		return ir.findAll();
	}
	
	public List<Item> getAll(String shop) {
		return ir.findAllOfShop(shop);
	}
	
	public Item getItemById(ItemId id) {
		return ir.getOne(id);
	}
	
	public List<Item> getAllAvaliable() {
		return ir.findAllAvaliable();
	}

	@Override
	public JpaRepository<Item, ItemId> getRepository() {
		return ir;
	}

}
