package com.smart.entity;

import java.util.ArrayList;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


//this is user class 


@Entity
@Table(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private  int id;
	
	
	// this is used for server side  validation
	
	@NotBlank(message = "Name field is required !!")
	@Size(min = 2 , max = 20 , message = "min 2 and max 20 character allowed")
	private String name;
	
	@Column(unique = true)
	
	private String email;
	
	private String password;
	
	// used for column length specific on database table
	
	@Column(length = 500)
	private String about;
	
	private  String role;
	
	
	private  String imageUrl;
	
	private boolean enable;
	
	
	
	// join user and contact
	//one to many relationship
	//cascadetypeAll  automatic execution
	//use Lazy loading
	//mapped by use for not create other table in database mapped through user table
	
	@OneToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY,mappedBy ="user")
private	List<Contact> contacts= new ArrayList<>(); 
	
	
	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", about=" + about
				+ ", role=" + role + ", imageUrl=" + imageUrl + ", enable=" + enable + ", contacts=" + contacts + "]";
	}
	
	
	
	
	
	
	

	

}
