package com.example.demo.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	 public List<User> getUsers() {
	        return List.of(
	        		new User(
	        				1L,
	        				"Ashutosh",
	        				"ashutoshas2610@gmail.com",
	        				LocalDate.of(2002, Month.OCTOBER,26),
	        				24
	        				)
	        		
	        		);
	    }
	 public String displayContent() {
		 return "Hello , This is student Content";
	 }
}
