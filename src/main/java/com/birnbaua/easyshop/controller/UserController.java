package com.birnbaua.easyshop.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.birnbaua.easyshop.auth.CustomUserDetailsService;
import com.birnbaua.easyshop.shop.order.User;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Log LOG = LogFactory.getLog(UserController.class);
	
	@Autowired
	private CustomUserDetailsService us;
	
	@GetMapping()
	public String getUser(HttpServletRequest request, Model model) {
		String name = null;
		try {
			name = request.getUserPrincipal().getName();
		} catch(NullPointerException e) {
			LOG.error(e.getMessage());
			return "redirect:/login";
		}
		User user = us.getByUsername(name);
		model.addAttribute("username", user.getUsername());
		model.addAttribute("firstName",user.getFirstName());
		model.addAttribute("lastName",user.getLastName());
		model.addAttribute("email", user.getEmail());
		return "showUser";
	}

}
