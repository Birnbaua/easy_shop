package com.birnbaua.easyshop.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.birnbaua.easyshop.auth.CustomUserDetailsService;
import com.birnbaua.easyshop.auth.UserRole;
import com.birnbaua.easyshop.log.LoggingHelper;
import com.birnbaua.easyshop.service.ItemService;
import com.birnbaua.easyshop.shop.order.User;

@Controller
public class HomeController {
	
	private static final Log LOG = LogFactory.getLog(UserController.class);
	
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
			LOG.info("Created new user with username: " + user.getUsername() + " and email: " + user.getEmail());
		} catch (ServletException e) {
			LOG.error("Something went wrong creating the user with username: " + user.getUsername() + " and email: " + user.getEmail());
			LoggingHelper.logStackTrace(LOG, e);
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
	
	@GetMapping("/shop/{shop}")
	public String getOverview(@PathVariable String shop, @RequestParam(required = true) String table, Model model) {
		model.addAttribute("table_nr", table);
		model.addAttribute("shop", shop);
		model.addAttribute("items",is.getAll(shop));
		return "user_order";
	}
	
}
