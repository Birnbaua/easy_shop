package com.birnbaua.easyshop.scheduler;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.birnbaua.easyshop.auth.CustomUserDetailsService;
import com.birnbaua.easyshop.auth.User;
import com.birnbaua.easyshop.auth.UserRole;

@EnableScheduling
public class StandardValues {
	
	@Autowired
	private CustomUserDetailsService us;
	
	@Scheduled(initialDelay=1000)
	public void createAdminAcc() {
		if(us.getByUsername("admin") != null) {
			User user = new User();
			user.setUsername("admin");
			user.setPassword("password");
			us.initSave(user);
			List<UserRole> roles = new LinkedList<>();
			roles.add(UserRole.ADMIN);
			us.changeUserRoles("admin", roles);
		}
	}
	
}
