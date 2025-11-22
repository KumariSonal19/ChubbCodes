package com.MongoSpring.MongoSpring.repository;

import com.MongoSpring.MongoSpring.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface StudentRepo extends MongoRepository<Student,Integer> {

}
