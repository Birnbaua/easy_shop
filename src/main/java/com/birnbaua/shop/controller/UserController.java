package com.birnbaua.shop.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.birnbaua.shop.auth.CustomUserDetailsService;
import com.birnbaua.shop.auth.UserRole;
import com.birnbaua.shop.order.User;
import com.birnbaua.shop.service.ItemService;

@Controller
public class UserController {
	
	@Autowired
	private CustomUserDetailsService us;
	
	@Autowired
	private ItemService is;
	
	@PostMapping("/signup")
	public String registered(HttpServletRequest request, @ModelAttribute(name="user") User user) {
		user.getRoles().add(UserRole.GUEST);
		String un = user.getUsername();
		String pw = user.getPassword();
		us.initSave(user);
		try {
			request.login(un, pw);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:/admin/orders";
	}
	
	@GetMapping("/")
	public String root(@RequestParam(required = true) String table) {
		return "redirect:/home" + "?table=" + table;
	}
	
	@GetMapping("/customLogout")
	public String logout(HttpServletRequest request) {
		try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:/home";
	}
	
	@GetMapping("/login")
	public String getLoginForm() {
		return "login";
	}
	
	@GetMapping("/about")
	public String modaltest() {
		return "about";
	}
	
	@GetMapping("/join")
	public String getRegisterForm() {
		return "register";
	}
	
	@GetMapping("/home")
	public String getOverview(@RequestParam(required = true) String table, Model model) {
		model.addAttribute("table_nr", table);
		model.addAttribute("items",is.getAllAvaliable());
		return "user_order";
	}

}
