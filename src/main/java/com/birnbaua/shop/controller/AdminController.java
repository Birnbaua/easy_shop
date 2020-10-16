package com.birnbaua.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@GetMapping()
	public String getAdminPage() {
		return "admin_page";
	}
	
	@GetMapping("/items")
	public String items() {
		return "item_table";
	}
	
	@GetMapping("/orders")
	public String orders() {
		return "order_table";
	}

}
