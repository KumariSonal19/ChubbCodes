package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Student;
import com.example.demo.repo.StudentRepo;

@Service
public class StudentService {

    @Autowired
    StudentRepo studentRepo;

    public Student addStudent(Student student) {
        return studentRepo.save(student);
    }
}
