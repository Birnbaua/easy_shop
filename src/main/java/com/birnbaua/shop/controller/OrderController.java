package com.birnbaua.shop.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.birnbaua.shop.order.Order;
import com.birnbaua.shop.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderService os;
	
	@PostMapping
	public ResponseEntity<Order> postOrder(@RequestBody Order order) {
		try {
			order.setOrderPos(order.getOrderPos().stream().filter(x -> x.getAmount() > 0).collect(Collectors.toList()));
			os.save(order);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().header("Order", "Something went wrong while creating your order").body(order);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("Order", "Successfully created an order").body(order);
	}
	
	@GetMapping
	public ResponseEntity<List<Order>> getOpenOrders() {
		List<Order> orders = null;
		try {
			orders = os.getOpenOrders();
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().header("Order", "Something went wrong fetching open orders from server").body(orders);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Order", "Successfully fetched all open orders").body(orders);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Order> editOrder(@PathVariable Long id) {
		Order order = null;
		try {
			order = os.setIsDone(id);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().header("Order", "Something went wrong while editing your order").body(order);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Order", "Successfully edited an order").body(order);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
		Order order = null;
		try {
			order = os.deleteById(id);
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().header("Order", "Something went wrong deleting your order").body(order);
		}
		return ResponseEntity.status(HttpStatus.OK).header("Order", "Successfully deleted an order").body(order);
	}
}
