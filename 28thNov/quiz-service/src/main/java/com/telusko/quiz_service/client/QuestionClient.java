package com.telusko.quiz_service.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.telusko.quiz_service.model.QuestionWrapper;
import com.telusko.quiz_service.model.ResponseDto;


@FeignClient("QUESTION-SERVICE")
public interface QuestionClient {
		
		@GetMapping("question/generate")
		public ResponseEntity<List<String>> getQuestionForQuiz(@RequestParam String category,@RequestParam Integer numQ);
		
		@PostMapping("question/getQuestions")
		public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<String> questionIds);
		
		@PostMapping("question/score")
		public ResponseEntity<Integer> getScore(@RequestBody List<ResponseDto> responses);
}