package com.telusko.quiz_service.service;

import com.telusko.quiz_service.model.Question;
import com.telusko.quiz_service.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public Question addQuestion(Question question) {
        return repository.save(question);
    }

    public List<Question> getQuestionsByCategory(String category) {
        return repository.findByCategory(category);
    }
}