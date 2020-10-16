package com.birnbaua.shop.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.birnbaua.shop.order.Item;
import com.birnbaua.shop.service.ItemService;

@RestController
@RequestMapping("/api/item")
public class ItemController {
	
	private static final Log LOG = LogFactory.getLog(ItemController.class);
	
	@Autowired
	private ItemService is;
	
	@PostMapping()
	public ResponseEntity<Item> postItem(@RequestBody Item item) {
		try {
			is.save(item);
			LOG.info("Created new item: " + item.getName() + " with price: " + item.getPrice() + " and a maximal amount: " + item.getMaxAmount());
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().header("Item", "Something went wrong while creating your item").body(item);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", "Successfully created an item").body(item);
	}
	
	@GetMapping()
	public ResponseEntity<List<Item>> getAll() {
		List<Item> items = null;
		try {
			items = is.getAll();
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().header("Item", "Something went wrong while fetching the items from the database").body(items);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", "Successfully fetched all items from the database").body(items);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Item> editItem(@PathVariable String id, @RequestBody Item item) {
		item.setName(id);
		try {
			is.save(item);
			LOG.info("Edited item: " + item.getName() + " with price: " + item.getPrice() + " and a maximal amount: " + item.getMaxAmount());
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().header("Item", "Something went wrong while editing your item").body(item);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", "Successfully edited an item").body(item);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Item> deleteItem(@PathVariable String id) {
		Item item = null;
		try {
			item = is.deleteById(id);
			LOG.info("Deleted item: " + item.getName() + " with price: " + item.getPrice() + " and a maximal amount: " + item.getMaxAmount());
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().header("Item", "Something went wrong while creating your item").body(item);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", "Successfully created an item").body(item);
	}

}
