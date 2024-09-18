package com.smart.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.smart.config.CustomUserDetails;
import com.smart.entity.User;

public class UserDetailServiceImp implements UserDetailsService {

	@Autowired
	
	private UserRepositry userRepositry;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		
		User user= userRepositry.getUserByName(username);
		
		if (user==null) {
			
			throw new UsernameNotFoundException("could not found user");
		}
		
		
		CustomUserDetails customUserDetails= new CustomUserDetails(user);
		
		return customUserDetails;
		
	}

}
