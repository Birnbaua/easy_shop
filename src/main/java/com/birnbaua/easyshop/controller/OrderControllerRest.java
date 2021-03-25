package com.birnbaua.easyshop.controller;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
import com.birnbaua.easyshop.service.OrderService;
import com.birnbaua.easyshop.service.ShopService;
import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.ShopTable;
import com.birnbaua.easyshop.shop.order.Order;
import com.birnbaua.easyshop.shop.order.id.OrderId;
import com.birnbaua.easyshop.shop.order.id.ShopTableId;

@RestController
@RequestMapping("/api/shop/{shop}/table/{table}/order")
public class OrderControllerRest {
	
	private static final Log LOG = LogFactory.getLog(OrderControllerRest.class);
	
	@Autowired
	private OrderService os;
	
	@Autowired
	private ShopService ss;

	private Map<String,AtomicLong> nrMap = Collections.synchronizedMap(new HashMap<String,AtomicLong>());
	
	//done
	@PostMapping
	public ResponseEntity<Order> postOrder(@PathVariable String shop, @PathVariable Integer table, @RequestBody Order order) {
		//check order number of shop
		if(!nrMap.containsKey(shop)) {nrMap.put(shop, new AtomicLong(os.getLastNr(shop)));}
		String msg = null;
		try {
			//perform check
			os.checkOrderCount(shop, table);
			//preprocess order
			preprocess(shop,table,order);
			LOG.info("Try saving oder of shop: " + shop + " and nr: " + order.getNr());
			if(order.getOrderPos().size() == 0) {
				return ResponseEntity.status(HttpStatus.OK).header("Order", "Tried to create empty order").body(null);
			}
			os.save(order);
			msg = "Successfully created an order with id: " + order.getNr();
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while creating your order. Error message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Order", msg).body(order);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Order", msg).body(order);
	}
	
	@GetMapping
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<List<Order>> getOpenOrders(@PathVariable String shop, @PathVariable Integer table, 
			@RequestParam(value = "from", required = false) Timestamp from, 
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, 
			@RequestParam(value = "pageSize", required = false, defaultValue = "45") Integer pageSize, HttpServletRequest request) {
		List<Order> orders = null;
		try {
			if(!isAuthorizedToManipulate(shop,request)) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Order", "User is not authorized to view this orders.").body(null);
			}
			if(from != null) {
				if(table.compareTo(-1) <= 0) {
					orders = os.findOpenOrders(shop,from,PageRequest.of(page, pageSize));
				} else {
					orders = os.findOpenOrders(shop,table,from,PageRequest.of(page, pageSize));
				}
			} else {
				if(table.compareTo(-1) <= 0) {
					orders = os.findOpenOrders(shop,PageRequest.of(page, pageSize));
				} else {
					orders = os.findOpenOrders(shop,table,PageRequest.of(page, pageSize));
				}
			}
			//orders = os.findAll(PageRequest.of(0, pageSize));
		} catch(Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Order", "Something went wrong fetching open orders of shop: " + shop + " from server").body(orders);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Order", "Successfully fetched all open orders of shop: " + shop).body(orders);
	}
	
	@PutMapping("/{nr}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Order> editOrder(@PathVariable String shop, @PathVariable Integer table, @PathVariable Long nr, @RequestBody Order order, HttpServletRequest request) {
		String msg = null;
		try {
			if(!isAuthorizedToManipulate(shop,request)) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Order", "User is not authorized to edit this item.").body(order);
			}
			preprocess(shop,table,nr,order);
			order = os.save(order);
			msg = "Successfully edited an order of " + shop + "and table " + table + " with nr: " + order.getNr();
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while edited your order. Error message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Order", msg).body(order);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Order", msg).body(order);
	}
	
	@DeleteMapping("/{nr}")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<Order> deleteOrder(@PathVariable String shop, @PathVariable Integer table, @PathVariable Long nr, HttpServletRequest request) {
		Order order = null;
		String msg = null;
		try {
			if(isAuthorizedToManipulate(shop,request) == false) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Order", "User is not authorized to edit this item.").body(order);
			}
			if(os.existsById(new OrderId(new ShopTableId(shop,table),nr))) {
				os.deleteById(new OrderId(new ShopTableId(shop,table),nr));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).header("Order", "Order not existing").body(null);
			}
			msg = "Successfully deleted order with id: " + nr;
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong deleting order with id: " + nr + ". Error Message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Order", msg).body(order);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Order", msg).body(order);
	}
	
	private boolean isAuthorizedToManipulate(String shop, HttpServletRequest request) {
		if(request.getUserPrincipal() != null) {
			if(request.isUserInRole("ROLE_ADMIN") || ss.getShopById(shop).getOwner().getUsername().equals(request.getUserPrincipal().getName())) {
				return true;
			}
		}
		return false;
	}
	
	private void preprocess(String shop, Integer table, Long nr, Order order) {
		order.setOrderPos(order.getOrderPos().stream().filter(x -> x.getAmount() > 0).collect(Collectors.toList()));
		AtomicInteger posNr = new AtomicInteger(0);
		order.getOrderPos().stream().forEach(x -> {
			x.setNr(posNr.getAndIncrement());
			x.setOrder(order);
		});
		order.setNr(nr);
		order.setTable(new ShopTable(new Shop(shop),table));
	}
	
	private void preprocess(String shop, Integer table, Order order) {
		order.setOrderPos(order.getOrderPos().stream().filter(x -> x.getAmount() > 0).collect(Collectors.toList()));
		AtomicInteger posNr = new AtomicInteger(0);
		order.getOrderPos().stream().forEach(x -> {
			x.setNr(posNr.getAndIncrement());
			x.setOrder(order);
		});
		order.setNr(nrMap.get(shop).incrementAndGet());
		order.setTable(new ShopTable(new Shop(shop),table));
	}
}
