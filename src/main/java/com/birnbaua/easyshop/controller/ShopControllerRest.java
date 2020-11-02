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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.birnbaua.easyshop.log.LoggingHelper;
import com.birnbaua.easyshop.service.ShopService;
import com.birnbaua.easyshop.shop.Shop;

@RestController
@RequestMapping("/api/shop")
public class ShopControllerRest {
	
	private static final Log LOG = LogFactory.getLog(ShopControllerRest.class);
	
	@Autowired
	private ShopService ss;
	
	@PostMapping
	@PreAuthorize("isAuthenticated() AND hasRole('ROLE_ADMIN')")
	public ResponseEntity<Shop> createShop(@RequestBody Shop shop, HttpServletRequest request) {
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
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Shop> getShop(@PathVariable String id, HttpServletRequest request) {
		if(!isAuthorized(id,request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Shop", "You are not authorized to view this shop").body(null);
		}
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
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<Shop>> getAllShops(@RequestParam(required = false) String owner, HttpServletRequest request) {
		List<Shop> shops = null;
		String msg = null;
		try {
			if(request.isUserInRole("ROLE_ADMIN")) {
				shops = ss.getAll();
			} else {
				shops = ss.getAllOfUser(request.getUserPrincipal().getName());
			}
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
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Shop> editShop(@PathVariable String id, @RequestBody Shop shop, HttpServletRequest request) {
		if(!isAuthorized(id,request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Shop", "You are not authorized to edit this shop").body(null);
		}
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
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Shop> deleteShop(@PathVariable String id, HttpServletRequest request) {
		if(!isAuthorized(id,request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Shop", "You are not authorized to delete this shop").body(null);
		}
		String msg = null;
		Shop shop = null;
		try {
			shop = ss.deleteById(id);
			msg = "Shop: " + shop.getName() + " with owner " + shop.getOwner() + " deleted.";
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while deleting the shop: " + id +". Error message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Shop", msg).body(shop);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Shop", msg).body(shop);
	}
	
	private boolean isAuthorized(String shop, HttpServletRequest request) {
		if(request.isUserInRole("ROLE_ADMIN")) {
			return true;
		}else if(ss.getShopById(shop).getOwner().getUsername().equals(request.getUserPrincipal().getName())) {
			return true;
		}
		return false;
	}

}
