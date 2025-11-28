package com.telusko.quiz_service.client;

import com.telusko.quiz_service.dto.QuestionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "QUESTION-SERVICE")
public interface QuestionClient {

    @GetMapping("/questions/{category}")
    List<QuestionDto> getQuestions(@PathVariable String category);
}