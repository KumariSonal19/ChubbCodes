package com.telusko.quiz_service.controller;

import com.telusko.quiz_service.model.Question;
import com.telusko.quiz_service.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        Question saved = service.addQuestion(question);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        List<Question> list = service.getQuestionsByCategory(category);
        return ResponseEntity.ok(list);
    }
}