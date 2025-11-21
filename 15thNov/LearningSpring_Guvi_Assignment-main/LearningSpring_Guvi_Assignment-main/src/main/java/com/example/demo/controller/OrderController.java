package com.example.demo.controller;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.Order;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path="api")
public class OrderController {
//	private final StudentService studentService;
//	@Autowired
//	public StudentController(StudentService studentService) {
//		this.studentService=studentService;
//	}
//	    @GetMapping("/getNames")
//	    public List<Student> getStudents() {
//	        return studentService.getStudents();
//	    }
//	    @GetMapping("/content")
//	    String displayContent() {
//	    	return studentService.displayContent();
//	    }
	    @GetMapping("/order")
	    String getOrder() {
	    	return "Get User Order";
	    }
	    @PostMapping("/order")
	    float saveOrder( @RequestBody @Valid Order order) {
	    	return order.gettotalAmount();
	    	
	    }
	   
}
