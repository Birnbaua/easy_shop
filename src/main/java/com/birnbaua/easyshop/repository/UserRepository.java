package com.birnbaua.easyshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.birnbaua.easyshop.auth.User;
import com.birnbaua.easyshop.auth.UserRole;


public interface UserRepository extends JpaRepository<User,String> {
	
	public Optional<User> findByUsername(String username);
	
	@Query("SELECT COUNT(u) FROM User u WHERE 'ADMIN' IN u.roles")
	public int getNrOfAdmins();
	
	@Query("SELECT u FROM User u WHERE u.username = ?1")
	public User getUsername(String username);
	
	@Query("SELECT u FROM User u")
	public List<User> getAll(UserRole role);
	
}
