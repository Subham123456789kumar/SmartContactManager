package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.objenesis.instantiator.basic.NewInstanceInstantiator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepositry;
import com.smart.entity.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


// this is controller class

@org.springframework.stereotype.Controller
public class Controller {
	
	
	// used @autowired automatically inject bean / object
	// CREAT  and inject bean at run time/ provide object
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserRepositry userRepositry;
	
	
	// Home page
	
	@RequestMapping("/home")
	public String home( Model model)

	{
		
		model.addAttribute("title", "this is home page");
		return"home";
	}
	
	
	
	// About page
	

	@GetMapping("/about")
	public String about( Model model)

	{
		
		model.addAttribute("title", "this is about page ");
		return"about";
	}
	
	
	// Signup page
	

	@GetMapping("/signup")
	public String signUp( Model model)

	{
		
		model.addAttribute("title", "this is signup page ");
		model.addAttribute("user", new User());

		return"signup";
	}
	
	

	@PostMapping("/"
			+ "do_register")
	public String  registerUser( @Valid  @ModelAttribute("user") User user, BindingResult result1,@RequestParam(value = "agreement" ,defaultValue = "false") boolean agreement, Model model,HttpSession session)
	{
		try {
			
			if (!agreement) {
				
				System.out.println("you have not agreed the term and condation");
				throw new Exception("you have not agreed the term and condation\"");
				
			}
			
			if (result1.hasErrors()) {
				
				System.out.println("Error"+result1.toString());
				
				model.addAttribute("user", user);
				
				return "signup";
			}
			
			user.setRole("ROLE_USER");
			user.setEnable(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			
			System.out.println("Aggrement"+agreement);
			
			System.out.println("User"+user);
			
			User result= this.userRepositry.save(user);
			model.addAttribute("user", new User() );
			
			session.setAttribute("message", new Message("Sucessfully Register !!" , "alert-success"));
			
			
			// use redirect when we reload the signup file  redirect signup page  and  delete  previous data  this is good  program  practice
			
			return "redirect:/signup";
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went Wrong !!" + e.getMessage() , "alert-danger"));
			
			return "signup";
		}
		
		
	}
	
	
	
	
	@GetMapping("/signin")
	public  String customLogin(Model model)
	{
		model.addAttribute("title", "Login page");
		
		return "login";
	}
	
	
	
	
	
}



