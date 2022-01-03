package com.epam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
public class User {
	@Id
	@Column(name = "USERNAME",updatable=false,nullable=false)
	String username;
	
	@Column(name="EMAIL")
	String email;
	
	@Column(name="NAME")
	String name;
	
	
	
}
