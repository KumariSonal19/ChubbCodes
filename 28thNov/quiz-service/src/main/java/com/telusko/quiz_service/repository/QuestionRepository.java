package com.telusko.quiz_service.repository;

import com.telusko.quiz_service.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByCategory(String category);
}