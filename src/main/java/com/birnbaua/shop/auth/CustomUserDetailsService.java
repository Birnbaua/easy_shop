package com.birnbaua.shop.auth;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.birnbaua.shop.order.User;
import com.birnbaua.shop.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		CustomUserDetails customUserDetails = optionalUser.map(user -> {return new CustomUserDetails(user);}).get();
		return new CustomUserDetails(customUserDetails);
	}
	
	public User getByUsername(String username) {
		return userRepository.findByUsername(username).orElseGet(null);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public List<User> getAll(UserRole role) {
		return userRepository.getAll(role);
	}
	
	public User changePW(User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public User initSave(User user) {
		if(!userRepository.findByUsername(user.getUsername()).isPresent()) {
			return changePW(user);
		} else {
			throw new EntityExistsException("User with username " + user.getUsername() + " already exists.");
		}
	}
	
	public User changeUserRoles(String username, List<UserRole> roles) {
		User user = getByUsername(username);
		if(user == null) {
			return changeUserRoles(user,roles);
		} else {
			throw new NullPointerException("User with username " + username + " does not exist.");
		}
	}
	
	public User changeUserRoles(User user, List<UserRole> roles) {
		user.setRoles(roles);
		return save(user);
	}
	
	public User changePW(String username, String password) {
		User user = getByUsername(username);
		user.setPassword(password);
		return changePW(user);
	}
	
	public User delete(String username) {
		User user = getByUsername(username);
		if(user.getRoles().contains(UserRole.ADMIN)) {
			if(userRepository.getNrOfAdmins() == 1) {
				throw new IllegalStateException("This is the last admin user which must not be deleted.");
			}
		}
		userRepository.delete(user);
		return user;
	}

}
