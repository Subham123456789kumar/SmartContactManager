package com.smart.config;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.smart.entity.User;

import jakarta.validation.constraints.AssertFalse.List;

public class CustomUserDetails implements UserDetails {

	
	private User user;
	
	
	
	
	
	public CustomUserDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		SimpleGrantedAuthority simpleGrantedAuthority= new SimpleGrantedAuthority(user.getRole());
		
		
		System.out.println(simpleGrantedAuthority);
		
        return Collections.singletonList(simpleGrantedAuthority);
		
	}

	@Override
	public String getPassword() {
		
		// this is foe testing purpose
		System.out.println(user.getPassword());

		return user.getPassword();
		
		
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

}
