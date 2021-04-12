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
import com.birnbaua.easyshop.service.ShopService;
import com.birnbaua.easyshop.service.ShopTableService;
import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.ShopTable;
import com.birnbaua.easyshop.shop.order.id.ShopTableId;

@RestController
@RequestMapping("/api/shop/{shop}/table")
public class ShopTableControllerRest {
	
	private static final Log LOG = LogFactory.getLog(ShopTableControllerRest.class);
	
	@Autowired
	private ShopTableService sts;
	
	@Autowired
	private ShopService ss;
	
	@PostMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ShopTable> postTable(@PathVariable String shop, @RequestBody ShopTable table, HttpServletRequest request) {
		String msg = null;
		try {
			if(isAuthorizedToManipulate(shop,request)) {
				table.setShop(new Shop(shop));
				table = sts.save(table);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Table", "You are not authorized to create a table.").body(null);
			}
			msg = "Created new table: " + table.getName() + " with table number: " + table.getNr() + ".";
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while creating your table. Error msg: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Table", msg).body(table);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Table", msg).body(table);
	}
	
	@GetMapping
	public ResponseEntity<List<ShopTable>> getAllTables(@PathVariable String shop, HttpServletRequest request) {
		List<ShopTable> tables = null;
		try {
			if(isAuthorizedToManipulate(shop,request)) {
				tables = sts.findTables(shop);
			} else {
				tables = sts.findAvaliableTables(shop);
			}
		} catch(Exception e) {
			LOG.error("Something went wrong while fetching all tables from shop: " + shop + ". Error msg: " + e.getMessage());
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Table", "Something went wrong while fetching the items of the shop: " + shop + " from the database").body(tables);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Table", "Successfully fetched all avaliable tables of the shop: " + shop + " from the database").body(tables);
	}
	
	@GetMapping("/{table}")
	public ResponseEntity<ShopTable> getTable(@PathVariable String shop, @PathVariable(value = "table") Integer nr, HttpServletRequest request) {
		String msg = null;
		ShopTable table = null;
		try {
			if(isAuthorizedToManipulate(shop,request)) {
				table = sts.findById(new ShopTableId(shop,nr));
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Table", "User is not authorized to view this table.").body(null);
			}
			if(table == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Table", "Table not existing").body(null);
			}
		} catch(Exception e) {
			msg = "Something went wrong while fetching your item from the database. Error msg: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Item", msg).body(table);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Item", msg).body(table);
	}
	
	@PutMapping("/{table}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ShopTable> editTable(@PathVariable String shop, @PathVariable(value="table") Integer nr, @RequestBody ShopTable table, HttpServletRequest request) {
		String msg = null;
		table.setNr(nr);
		table.setShop(new Shop(shop));
		try {
			if(isAuthorizedToManipulate(shop,request)) {
				table = sts.save(table);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Table", "User is not authorized to edit this table.").body(null);
			}
			msg = "Edited table: " + nr + " of shop: " + shop;
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while editing your table. Error msg: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Table", msg).body(table);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Table", msg).body(table);
	}
	
	@DeleteMapping("/{table}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<ShopTable> deleteTable(@PathVariable String shop, @PathVariable(value = "table") Integer nr, HttpServletRequest request) {
		String msg = null;
		try {
			if(isAuthorizedToManipulate(shop,request)) {
				if(sts.existsById(new ShopTableId(shop,nr))) {
					sts.deleteById(new ShopTableId(shop,nr));
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Table", "Table not existing").body(null);
				}
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Table", "User is not authorized to delete this table.").body(null);
			}
			msg = "Deleted table: " + nr + " of shop: " + shop;
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while deleting your table. Error msg: " + e.getMessage();
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Table", msg).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Table", msg).body(null);
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
