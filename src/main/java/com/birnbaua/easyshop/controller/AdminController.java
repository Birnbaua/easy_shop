package com.birnbaua.easyshop.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.birnbaua.easyshop.service.ShopService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private ShopService ss;
	
	@GetMapping()
	public String getAdminPage(HttpServletRequest request) {
		if(request.isRequestedSessionIdValid()) {
			return "shop_table";	
		} else {
			return "redirect:/login";
		}
	}
	
	@GetMapping("/{name}/items")
	public String items(@PathVariable String name, Model model, HttpServletRequest request) {
		model.addAttribute("shop", name);
		try {
			if(isAuthorized(name,request)) {
				return "item_table";	
			} else {
				return "redirect:/login";
			}
		} catch(NullPointerException e) {
			return "error";
		}
	}
	
	@GetMapping("/{name}/orders")
	public String orders(@PathVariable String name, Model model, HttpServletRequest request) {
		model.addAttribute("shop", name);
		try {
			if(isAuthorized(name,request)) {
				return "order_table";	
			}
			return "redirect:/login";
		} catch(NullPointerException e) {
			return "error";
		}
	}
	
	@GetMapping("/{name}/tables")
	public String tables(@PathVariable String name, Model model, HttpServletRequest request) {
		model.addAttribute("shop",name);
		try {
			if(isAuthorized(name,request)) {
				return "shopTable_overview";
			}
			return "redirect:/login";
		} catch(NullPointerException e) {
			return "error";
		}
	}
	
	private boolean isAuthorized(String shop, HttpServletRequest request) throws NullPointerException {
		if(request.isUserInRole("ROLE_ADMIN")) {
			return true;
		} else if(ss.getShopById(shop).getOwner().getUsername().equals(request.getUserPrincipal().getName())) {
			return true;
		}
		return false;
	}
}
