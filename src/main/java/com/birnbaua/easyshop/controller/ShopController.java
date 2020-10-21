package com.birnbaua.easyshop.controller;

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

import com.birnbaua.easyshop.log.LoggingHelper;
import com.birnbaua.easyshop.service.ShopService;
import com.birnbaua.easyshop.shop.Shop;

@RestController
@RequestMapping("/api/shop")
public class ShopController {
	
	private static final Log LOG = LogFactory.getLog(ShopController.class);
	
	@Autowired
	private ShopService ss;
	
	@PostMapping
	public ResponseEntity<Shop> createShop(@RequestBody Shop shop) {
		String msg = null;
		try {
			ss.save(shop);
			msg = "New shop: " + shop.getName() + " with owner " + shop.getOwner() + " created.";
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while creating the new shop: " + shop.getName() +". Error message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Shop", msg).body(shop);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Shop", msg).body(shop);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Shop> getShop(@PathVariable String id) {
		Shop shop = null;
		String msg = null;
		try {
			shop= ss.getShopById(id);
			msg = "Successfully fetched shop with name: " + id + " from database.";
		} catch(Exception e) {
			msg = "Something went wrong while fetching shop with name: " + id + " from the database. Error message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Shop", msg).body(shop);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Shop", msg).body(shop);
	}
	
	@GetMapping
	public ResponseEntity<List<Shop>> getAllShops() {
		List<Shop> shops = null;
		String msg = null;
		try {
			shops = ss.getAll();
			msg = "All existing shops in the database";
		} catch(Exception e) {
			msg = "Something went wrong while fetching all shops from the database. Error message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Shop", msg).body(shops);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Shop", msg).body(shops);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Shop> editShop(@PathVariable String id, @RequestBody Shop shop) {
		String msg = null;
		shop.setName(id);
		try {
			ss.save(shop);
			msg = "Shop: " + shop.getName() + " with owner " + shop.getOwner() + " edited.";
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while editing the shop: " + shop.getName() +". Error message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Shop", msg).body(shop);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Shop", msg).body(shop);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Shop> deleteShop(@PathVariable String id) {
		String msg = null;
		Shop shop = null;
		try {
			shop = ss.deleteById(id);
			msg = "Shop: " + shop.getName() + " with owner " + shop.getOwner() + " deleted.";
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while deleting the shop: " + shop.getName() +". Error message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Shop", msg).body(shop);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Shop", msg).body(shop);
	}

}
