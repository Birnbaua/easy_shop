package com.birnbaua.easyshop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.birnbaua.easyshop.service.ShopService;
import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.item.Item;
import com.birnbaua.easyshop.shop.order.id.ItemId;

@RestController
@RequestMapping("/api/shop/{shop}/item")
public class ItemControllerRest {
	
	private static final Log LOG = LogFactory.getLog(ItemControllerRest.class);
	
	@Autowired
	private ItemService is;
	
	@Autowired
	private ShopService ss;
	
	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Item> postItem(@PathVariable String shop, @RequestBody Item item, HttpServletRequest request) {
		String msg = null;
		try {
			item.setShop(new Shop(shop));
			if(isAuthorizedToManipulate(shop,request)) {
				item = is.save(item);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Item", "User is not authorized to create this item.").body(null);
			}
			msg = "Created new item: " + item.getName() + " with price: " + item.getPrice() + " and a maximal amount: " + item.getMaxAmount();
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while creating your item. Error msg: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Item", msg).body(item);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", msg).body(item);
	}
	
	@GetMapping
	public ResponseEntity<List<Item>> getAllItems(@PathVariable String shop, HttpServletRequest request) {
		List<Item> items = null;
		try {
			if(isAuthorizedToManipulate(shop,request)) {
				items = is.findItems(shop);
			} else {
				items = is.findAvaliableItems(shop);
			}
		} catch(Exception e) {
			LOG.error("Something went wrong while fetching all items from shop: " + shop + ". Error msg: " + e.getMessage());
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Item", "Something went wrong while fetching the items of the shop: " + shop + " from the database").body(items);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Item", "Successfully fetched all avaliable items of the shop: " + shop + " from the database").body(items);
	}
	
	@GetMapping("/{name}")
	public ResponseEntity<Item> getItem(@PathVariable String shop, @PathVariable String name, HttpServletRequest request) {
		String msg = null;
		Item item = null;
		try {
			if(isAuthorizedToManipulate(shop,request)) {
				item = is.findById(new ItemId(shop,name));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Item", "User is not authorized to edit this item.").body(null);
			}
			if(item == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Item", "Item not existing").body(null);
			}
		} catch(Exception e) {
			msg = "Something went wrong while fetching your item from the database. Error msg: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Item", msg).body(item);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", msg).body(item);
	}
	
	@PutMapping("/{name}")
	@PreAuthorize("isAuthorized()")
	public ResponseEntity<Item> editItem(@PathVariable String shop, @PathVariable String name, @RequestBody Item item, HttpServletRequest request) {
		String msg = null;
		item.setName(name);
		item.setShop(new Shop(shop));
		try {
			if(isAuthorizedToManipulate(shop,request)) {
				item = is.save(item);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Item", "User is not authorized to edit this item.").body(null);
			}
			msg = "Edited item: " + item.getName() + " with price: " + item.getPrice() + " and a maximal amount: " + item.getMaxAmount();
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while editing your item. Error msg: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Item", msg).body(item);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", msg).body(item);
	}
	
	@DeleteMapping("/{name}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Item> deleteItem(@PathVariable String shop, @PathVariable String name, HttpServletRequest request) {
		String msg = null;
		try {
			if(isAuthorizedToManipulate(shop,request)) {
				if(is.existsById(new ItemId(shop,name))) {
					is.deleteById(new ItemId(shop,name));
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Item", "Item not existing").body(null);
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Item", "User is not authorized to delete this item.").body(null);
			}
			msg = "Deleted item: " + name + " of shop: " + shop;
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while deleting your item. Error msg: " + e.getMessage();
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Item", msg).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Item", msg).body(null);
	}
	
	private boolean isAuthorizedToManipulate(String shop, HttpServletRequest request) {
		if(request.getUserPrincipal() != null) {
			if(request.isUserInRole("ROLE_ADMIN") || ss.getShopById(shop).getOwner().getUsername().equals(request.getUserPrincipal().getName())) {
				return true;
			}
		}
		return false;
	}
}
