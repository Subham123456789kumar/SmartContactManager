package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entity.User;



// used for perform curd  operation 

public interface UserRepositry extends JpaRepository<User, Integer> {
	
	@Query("select u from User u where u.email = :email")
	public User getUserByName(@Param("email") String email);
		
	

}
