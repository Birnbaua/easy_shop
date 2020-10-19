package com.birnbaua.shop.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.birnbaua.shop.log.LoggingHelper;
import com.birnbaua.shop.order.Order;
import com.birnbaua.shop.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	private static final Log LOG = LogFactory.getLog(OrderController.class);
	
	@Autowired
	private OrderService os;
	
	@PostMapping
	public ResponseEntity<Order> postOrder(@RequestBody Order order) {
		String msg = null;
		try {
			order.setOrderPos(order.getOrderPos().stream().filter(x -> x.getAmount() > 0).collect(Collectors.toList()));
			os.save(order);
			msg = "Successfully created an order with id: " + order.getId();
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
	public ResponseEntity<List<Order>> getOpenOrders() {
		List<Order> orders = null;
		try {
			orders = os.getOpenOrders();
		} catch(Exception e) {
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Order", "Something went wrong fetching open orders from server").body(orders);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Order", "Successfully fetched all open orders").body(orders);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Order> editOrder(@PathVariable Long id, @RequestBody Order order) {
		String msg = null;
		order.setId(id);
		try {
			order.setOrderPos(order.getOrderPos().stream().filter(x -> x.getAmount() > 0).collect(Collectors.toList()));
			os.save(order);
			msg = "Successfully edited an order with id: " + order.getId();
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong while edited your order. Error message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Order", msg).body(order);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Order", msg).body(order);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
		Order order = null;
		String msg = null;
		try {
			order = os.deleteById(id);
			msg = "Successfully deleted order with id: " + id;
			LOG.info(msg);
		} catch(Exception e) {
			msg = "Something went wrong deleting order with id: " + id + ". Error Message: " + e.getMessage();
			LOG.error(msg);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.badRequest().header("Order", msg).body(order);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Order", msg).body(order);
	}
}
