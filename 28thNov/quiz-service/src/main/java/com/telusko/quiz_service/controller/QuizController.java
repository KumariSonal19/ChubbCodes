package com.telusko.quiz_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.quiz_service.model.QuestionWrapper;
import com.telusko.quiz_service.model.Quiz;
import com.telusko.quiz_service.model.QuizDto;
import com.telusko.quiz_service.model.ResponseDto;
import com.telusko.quiz_service.service.QuizService;


@RestController
@RequestMapping("quiz")
public class QuizController {
	
	@Autowired
	QuizService quizService;
	
	@PostMapping("create")
	public ResponseEntity<Quiz> createQuiz(@RequestBody QuizDto quizdto){
		return quizService.createQuiz(quizdto.getCategoryName(),quizdto.getNumQ(),quizdto.getTitle());
	}
	
	@GetMapping("get/{quizId}")
	public ResponseEntity<List<QuestionWrapper>> createQuiz(@PathVariable String quizId){
		return quizService.getQuiz(quizId);
	}
	
	@PostMapping("submit/{quizId}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable String quizId,@RequestBody List<ResponseDto> responses){
		return quizService.submitQuiz(quizId,responses);
	}
}