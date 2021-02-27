package com.birnbaua.easyshop.controller;

import java.security.Principal;
import java.util.LinkedList;

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
import com.birnbaua.easyshop.auth.User;
import com.birnbaua.easyshop.auth.UserRole;
import com.birnbaua.easyshop.log.LoggingHelper;
import com.birnbaua.easyshop.service.ItemService;
import com.birnbaua.easyshop.service.ShopService;
import com.birnbaua.easyshop.service.ShopTableService;
import com.birnbaua.easyshop.shop.order.id.ShopTableId;

@Controller
public class HomeController {
	
	private static final Log LOG = LogFactory.getLog(UserController.class);
	
	@Autowired
	private CustomUserDetailsService us;
	
	@Autowired
	private ItemService is;
	
	@Autowired
	private ShopService ss;
	
	@Autowired
	private ShopTableService sts;
	
	@PostMapping("/signup")
	public String registered(HttpServletRequest request, @ModelAttribute(name="user") User user) {
		user.setRoles(new LinkedList<>());
		user.getRoles().add(UserRole.GUEST);
		String un = user.getUsername();
		String pw = user.getPassword();
		try {
			us.initSave(user);
			request.login(un, pw);
			LOG.info("Created new user with username: " + user.getUsername() + " and email: " + user.getEmail());
		} catch (ServletException e) {
			LOG.error("Something went wrong creating the user with username: " + user.getUsername() + " and email: " + user.getEmail());
			LoggingHelper.logStackTrace(LOG, e);
		}
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String root(Model model) {
		return "redirect:/shop";
	}
	
	@GetMapping("/customLogout")
	public String logout(HttpServletRequest request) {
		try {
			request.logout();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	/**
	 * Login form
	 * @return
	 */
	@GetMapping("/login")
	public String getLoginForm() {
		return "login";
	}
	
	@GetMapping("/about")
	public String modaltest() {
		return "about";
	}
	
	/**
	 * To register a new user
	 * @return
	 */
	@GetMapping("/join")
	public String getRegisterForm() {
		return "register";
	}
	
	@GetMapping("/shop")
	public String shop(Model model) {
		try {
			model.addAttribute("shops", ss.findAll());
		} catch(Exception e) {}
		return "shop_overview";
	}
	
	@GetMapping("/shop/{shop}")
	public String getOverview(@PathVariable String shop, @RequestParam(value = "table", required = false) Integer table, Model model, HttpServletRequest request) {
		if(ss.existsById(shop) == false) {
			return "error";
		}
		if(table == null) {
			try {
				model.addAttribute("shop",shop);
				if(isAuthorized(request,shop)) {
					return "redirect:/admin/" + shop;
				}
				model.addAttribute("tables", sts.findTables(shop));
			} catch(Exception e) {}
			return "shop_details";
		} else {
			try {
				model.addAttribute("table_name", sts.findById(new ShopTableId(shop,table)).getName());
				model.addAttribute("shop", shop);
				model.addAttribute("table_nr", table);
				model.addAttribute("shop_title", ss.findById(shop).getTitle());
				model.addAttribute("items",is.findAvaliableItems(shop));
			} catch(Exception e) {}
			return "user_order";
		}
		
	}
	
	private boolean isAuthorized(HttpServletRequest request, String shop) {
		if(request.getUserPrincipal() != null) {
			if(request.isUserInRole("ROLE_ADMIN") || ss.findById(shop).getOwner().getUsername().equals(request.getUserPrincipal().getName())) {
				return true;
			}
		}
		return false;
	}
	
}
