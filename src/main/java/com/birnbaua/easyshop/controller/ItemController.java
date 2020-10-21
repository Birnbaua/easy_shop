package com.birnbaua.easyshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.birnbaua.easyshop.log.LoggingHelper;
import com.birnbaua.easyshop.service.ItemService;
import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.order.Item;
import com.birnbaua.easyshop.shop.order.id.ItemId;

@RestController
@RequestMapping("/api/{shop}/item")
public class ItemController {
	
	private static final Log LOG = LogFactory.getLog(ItemController.class);
	
	@Autowired
	private ItemService is;
	
	@PostMapping()
	public ResponseEntity<Item> postItem(@PathVariable String shop, @RequestBody Item item) {
		String msg = null;
		try {
			item.setShop(new Shop(shop));
			is.save(item);
			msg = "Created new item: " + item.getName() + " with price: " + item.getPrice() + " and a maximal amount: " + item.getMaxAmount();
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while creating your item. Error msg: " + e.getMessage();
			e.printStackTrace();
//			LOG.error(msg);
//			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Item", msg).body(item);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", msg).body(item);
	}
	
	@GetMapping()
	public ResponseEntity<List<Item>> getAll(@PathVariable String shop) {
		List<Item> items = null;
		try {
			items = is.getAll(shop);
		} catch(Exception e) {
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Item", "Something went wrong while fetching the items of the shop: " + shop + " from the database").body(items);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", "Successfully fetched all avaliable items of the shop: " + shop + " from the database").body(items);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Item> editItem(@PathVariable String shop, @PathVariable String name, @RequestBody Item item, HttpServletRequest request) {
		String msg = null;
		item.setName(name);
		item.setShop(new Shop(shop));
		if(!isAuthorizedToManipulate(new ItemId(shop,name),request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Item", "User is not authorized to edit this item.").body(item);
		}
		try {
			is.save(item);
			msg = "Edited new item: " + item.getName() + " with price: " + item.getPrice() + " and a maximal amount: " + item.getMaxAmount();
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while editing your item. Error msg: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Item", msg).body(item);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", msg).body(item);
	}
	
	/*
	 * at the moment also deleting if the shop path variable is the wrong one, only the id is important
	 */
	@DeleteMapping("/{nr}")
	public ResponseEntity<Item> deleteItem(@PathVariable String shop, @PathVariable String name, HttpServletRequest request) {
		Item item = null;
		String msg = null;
		if(!isAuthorizedToManipulate(new ItemId(shop,name),request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Item", "User is not authorized to delete this item.").body(item);
		}
		try {
			item = is.deleteById(new ItemId(shop,name));
			msg = "Deleted item: " + item.getName() + " with price: " + item.getPrice() + " and a maximal amount: " + item.getMaxAmount();
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while deleting your item. Error msg: " + e.getMessage();
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Item", msg).body(item);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", msg).body(item);
	}
	
	private boolean isAuthorizedToManipulate(ItemId itemId, HttpServletRequest request) {
		if(request.isUserInRole("ROLE_ADMIN")) {
			return true;
		} else if(is.getItemById(itemId).getShop().getOwner().getUsername().equals(request.getUserPrincipal().getName())) {
			return true;
		}
		return false;
	}
}
