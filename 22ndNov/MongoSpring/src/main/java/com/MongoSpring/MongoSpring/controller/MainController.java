package com.MongoSpring.MongoSpring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.MongoSpring.MongoSpring.model.Student;
import com.MongoSpring.MongoSpring.repository.StudentRepo;

@RestController
public class MainController {
	
	@Autowired
	StudentRepo studentrepo;
	
	@PostMapping("/addStudent")
	public void addStudent(@RequestBody Student student) {
		studentrepo.save(student);
	}
	
	@GetMapping("/getStudent/{id}")
	public Student getStudent(@PathVariable Integer id) {
		return studentrepo.findById(id).orElse(null);
	}
	
	@GetMapping("/fetchStudents")
	public List<Student> fetchStudents() {
		return studentrepo.findAll();
	}
	
	@PutMapping("/updateStudent")
	public void updateStudent(@RequestBody Student student) {
		Student data=studentrepo.findById(student.getRno()).orElse(null);
		if(data!=null) {
			data.setName(student.getName());
			data.setAddress(student.getAddress());
			studentrepo.save(data);
		}
	}
	
	@DeleteMapping("/deleteStudent/{id}")
	public void deleteStudent(@PathVariable Integer id) {
		studentrepo.deleteById(id);
	}
}
