package com.birnbaua.easyshop.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
import com.birnbaua.easyshop.service.OrderService;
import com.birnbaua.easyshop.shop.Shop;
import com.birnbaua.easyshop.shop.order.Order;
import com.birnbaua.easyshop.shop.order.id.OrderId;

@RestController
@RequestMapping("/api/{shop}/order")
public class OrderControllerRest {
	
	private static final Log LOG = LogFactory.getLog(OrderControllerRest.class);
	
	@Autowired
	private OrderService os;

	private Map<String,AtomicLong> nrMap = Collections.synchronizedMap(new HashMap<String,AtomicLong>());
	
	@PostMapping
	public ResponseEntity<Order> postOrder(@PathVariable String shop, @RequestBody Order order) {
		if(!nrMap.containsKey(shop)) {
			nrMap.put(shop, new AtomicLong(os.getLastNr(shop)));
		}
		String msg = null;
		try {
			order.setOrderPos(order.getOrderPos().stream().filter(x -> x.getAmount() > 0).collect(Collectors.toList()));
			order.setNr(nrMap.get(shop).incrementAndGet());
			LOG.info("Try saving oder of shop: " + order.getShop().getName() + " and nr: " + order.getNr());
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
	public ResponseEntity<List<Order>> getOpenOrders(@PathVariable String shop) {
		List<Order> orders = null;
		try {
			orders = os.getOpenOrders(shop);
		} catch(Exception e) {
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Order", "Something went wrong fetching open orders of shop: " + shop + " from server").body(orders);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Order", "Successfully fetched all open orders of shop: " + shop).body(orders);
	}
	
	@PutMapping("/{nr}")
	public ResponseEntity<Order> editOrder(@PathVariable String shop, @PathVariable Long nr, @RequestBody Order order, HttpServletRequest request) {
		String msg = null;
		if(!isAuthorizedToManipulate(new OrderId(shop,nr),request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Order", "User is not authorized to edit this item.").body(order);
		}
		order.setNr(nr);
		order.setShop(new Shop(shop));
		try {
			order.setOrderPos(order.getOrderPos().stream().filter(x -> x.getAmount() > 0).collect(Collectors.toList()));
			os.save(order);
			msg = "Successfully edited an order with id: " + order.getNr();
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while edited your order. Error message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Order", msg).body(order);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Order", msg).body(order);
	}
	
	@DeleteMapping("/{nr}")
	public ResponseEntity<Order> deleteOrder(@PathVariable String shop, @PathVariable Long nr, HttpServletRequest request) {
		Order order = null;
		String msg = null;
		if(!isAuthorizedToManipulate(new OrderId(shop,nr),request)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header("Order", "User is not authorized to edit this item.").body(order);
		}
		try {
			order = os.deleteById(new OrderId(shop,nr));
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
	
	private boolean isAuthorizedToManipulate(OrderId orderId, HttpServletRequest request) {
		if(request.isUserInRole("ROLE_ADMIN")) {
			return true;
		} else if(os.getOrderById(orderId).getShop().getOwner().getUsername().equals(request.getUserPrincipal().getName())) {
			return true;
		}
		return false;
	}
}
