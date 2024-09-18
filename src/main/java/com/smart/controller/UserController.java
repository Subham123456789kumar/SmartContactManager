package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepositry;
import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.helper.Message;

import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private UserRepositry userRepositry;
	
	
	@Autowired
	private ContactRepository contactRepository;
	
	@ModelAttribute
	public void addCommonData(Model model , Principal principal) {
		

		String userName= principal.getName();
		
		System.out.println(userName);
		
		User user=userRepositry.getUserByName(userName);
		
		model.addAttribute("user", user);
		
		
	}
	
	
	
	@RequestMapping("/index")
	public String dashboard(Model model , Principal principal)
	{
		model.addAttribute("title", "Add Contact");
		return "normal/dashboard";
	}

	
	// open add form handler
	
	@GetMapping("/add_contact")
	
	public String openAddContactForm(Model model) {
		
		
		model.addAttribute("title", "Add Contact");
		model.addAttribute("contact", new Contact());
		
		
		return "normal/add_contact_form";
		
		
	}
	
	
	
	
	
	
	@PostMapping("/" + "process_contact")

	//@PostMapping("/"+ "do_register")
	public String processContact(@ModelAttribute Contact contact,@RequestParam("imageprofile") MultipartFile file ,Principal principal,HttpSession session) {
		
		
		
		try {
		
		String name=principal.getName();
		
		User user=this.userRepositry.getUserByName(name);
		
		
		if (file.isEmpty()) {
			
			
			System.out.println("file is empty");
			contact.setImage("contact.jpg");
			
		}
		
		else {
			
			contact.setImage(file.getOriginalFilename());
			
			File saveFile=new ClassPathResource("static/image").getFile();
			
			
			
			Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			
		      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
		}
		
		user.getContacts().add(contact);

		contact.setUser(user);
		this.userRepositry.save(user);
		
		
		
		
		System.out.println("DATA" + contact);
		
		
		
		System.out.println("add to database ");
		
		
		
		session.setAttribute("message", new Message("your contact is added successfully" ,"success"));
		
		
		}
		catch (Exception e) {
			// TODO: handle exception
			session.setAttribute("message", new Message("something went wrong" ,"danger"));

			e.printStackTrace();
			
		}
		
	    return "redirect:/user/add_contact";

	}
	
	
	@GetMapping("/show-contacts/{page}")
	
	// per page =s[n]
	// current page =0 page
	public String showContacts(@PathVariable("page") Integer page, Model m ,Principal principal) {
		
		m.addAttribute("title", "Show user contacts");
		
		String userName= principal.getName();
		
		User user= this.userRepositry.getUserByName(userName);
		
		//used for pagination
		
		//current  -page
		// contact per page -5
		
		
		Pageable pageable= PageRequest.of(page,3);
		
		
		
		Page<Contact> contacts= this.contactRepository.findContactByUser(user.getId(), pageable);
		m.addAttribute("contacts", contacts);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalpages", contacts.getTotalPages());
		
		return "normal/show_contacts";
		
	}
	
	
	//show particular contact detail
	
	@RequestMapping("/{cid}/contact")
	public String showContactDetail(@PathVariable("cid") Integer cid ,Model model , Principal principal)
	{
		
	Optional<Contact> conOptional =this.contactRepository.findById(cid);
		
	Contact contact=conOptional.get();
	
	String userName=principal.getName();
	User user=this.userRepositry.getUserByName(userName);
	
	System.out.println(user.getId());
	System.out.println(contact.getUser().getId());
	
	if (user.getId() == contact.getUser().getId()) {
		
		model.addAttribute("contact", contact);
		model.addAttribute("title", contact.getName());
		
	}
	
	
	
		
		return "normal/contact_detail";
	}
	
	
	//delete contact handler
	@GetMapping("/delete/{cid}")
	public String deleteContact(@PathVariable("cid") Integer cid , Model model ,HttpSession session, Principal principal)
	{
		System.out.println("CID"+cid);

	 
		
		 Contact contact =this.contactRepository.findById(cid).get();
		
		
		System.out.println("contact"+contact.getCid());
		
		contact.setUser(null);
		
		//remove 
		//image
		//contact.getiamge();
		
		
		
		
	
		this.contactRepository.delete(contact);
		
		session.setAttribute("message", new Message("Contact deleted succesfully...","success"));
		
		
		
		return"redirect:/user/show-contacts/0";
	}
	
	
	
	
	// update contact 
	
	@PostMapping("/update-contact/{cid}")
	public String updateForm(@PathVariable("cid") Integer cid , Model m)
	{
		m.addAttribute("title", "Update Contact");
		
		Contact contact = this.contactRepository.findById(cid).get();
		
		m.addAttribute("contact", contact);
	
		return "normal/update_form";
	}
	
	
	
	
	
}
