package com.telusko.quiz_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.telusko.quiz_service.model.Quiz;


public interface QuizRepository extends MongoRepository<Quiz,String>{

}