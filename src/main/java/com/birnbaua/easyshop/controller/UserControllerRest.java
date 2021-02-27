package com.birnbaua.easyshop.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.birnbaua.easyshop.auth.CustomUserDetailsService;
import com.birnbaua.easyshop.auth.PasswordChange;
import com.birnbaua.easyshop.auth.User;
import com.birnbaua.easyshop.log.LoggingHelper;

@RestController
@RequestMapping("/api/user")
public class UserControllerRest {

	private static final Log LOG = LogFactory.getLog(UserControllerRest.class);
	
	@Autowired
	private CustomUserDetailsService us;
	
	@PostMapping
	public ResponseEntity<User> join(@RequestBody User user) {
		try {
			LOG.info("Try creating user with username: " + user.getUsername());
			us.initSave(user);
			LOG.info("Successfully created user with username: " + user.getUsername());
		} catch(Exception e) {
			LOG.error("Something went wrong creating user with username: " + user.getUsername());
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.status(HttpStatus.CONFLICT).header("User", e.getMessage()).body(null);
		}
		return ResponseEntity.status(HttpStatus.CREATED).header("User", "Created new user with username: " + user.getUsername()).body(user);
	}
	
	@PreAuthorize("(#username == authentication.principal.username) OR hasRole('ROLE_ADMIN')")
	@GetMapping("/{username}")
	public ResponseEntity<User> getUser(@PathVariable(value = "username") String username) {
		return ResponseEntity.status(HttpStatus.OK).header("User", "User with username: " + username).body(us.getByUsername(username));
	}

	@PreAuthorize("(#username == authentication.principal.username) OR hasRole('ROLE_ADMIN')")
	@PutMapping("/{username}")
	public ResponseEntity<User> editUser(@PathVariable(value = "username") String username, @RequestBody User user) {
		User u = us.getByUsername(username);
		if(u != null) {
			u.setEmail(user.getEmail());
			u.setFirstName(user.getFirstName());
			u.setLastName(user.getLastName());
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("User", "No user with username ").body(null);
		}
		return ResponseEntity.status(HttpStatus.OK).header("User", "Edited user").body(us.save(u));
	}
	
	@PreAuthorize("(#username == authentication.principal.username) OR hasRole('ROLE_ADMIN')")
	@PutMapping("/{username}/password")
	public ResponseEntity<User> editPassword(HttpServletRequest request, @RequestBody PasswordChange change, @PathVariable(value = "username") String username) {
		User u = null;
		LOG.info("calledEdit");
		if(change.getPassword() != null && change.getOldPassword() != null) {
			try {
				u = us.getByUsername(username);
				LOG.info("Try changing password of user " + username);
				if(BCrypt.checkpw(change.getOldPassword(), u.getPassword())) {
					u = us.changePW(username, change.getPassword());
				} else {
					LOG.warn("Old password is not correct");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("User", "Wrong password").body(null);
				}
				LOG.info("Changed password of user " + username + " successfully");
			} catch(Exception e) {
				LOG.error("Something went wrong while changing password of user with username: " +  username);
				LoggingHelper.logStackTrace(LOG, e);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("User", e.getMessage()).body(null);
			}
		} else {
			LOG.info("password must not be null");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("User", "Password must not be null.").body(u);
		}
		return ResponseEntity.status(HttpStatus.OK).header("User", "Password of user with username " + username + " changed.").body(u);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/{username}/userroles")
	public ResponseEntity<User> editUserRoles(@RequestBody User user, @PathVariable(value = "username") String username) {
		User u = null;
		try {
			LOG.info("Try changing roles of user with username: " + username);
			u = us.changeUserRoles(username, user.getRoles());
			LOG.info("Successfully changed roles of user with username: " + username);
		} catch(Exception e) {
			LOG.error("Something went wrong while changing the roles of user with username: " + username);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("User", e.getMessage()).body(u);
		}
		return ResponseEntity.status(HttpStatus.OK).header("User", "Roles for user with username " + username + " changed.").body(u);
	}
	
	@PreAuthorize("(#username == authentication.principal.username) OR hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{username}")
	public ResponseEntity<User> deleteUser(@PathVariable(value = "username") String username) {
		User u = null;
		try {
			LOG.info("Try deleting user with username: " + username);
			u = us.delete(username);
			LOG.info("Successfully deleted user with username: " + username);
		} catch(Exception e) {
			LOG.error("Something went wrong while deleting user with username: " + username);
			LoggingHelper.logStackTrace(LOG, e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("User", e.getMessage()).body(u);
		}
		return ResponseEntity.status(HttpStatus.OK).header("User", "Deleted user with username " + username).body(u);
	}

	
}
