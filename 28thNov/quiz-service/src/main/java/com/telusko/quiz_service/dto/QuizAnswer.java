package com.telusko.quiz_service.dto;

import lombok.Data;

@Data
public class QuizAnswer {
    private String questionId;
    private int selectedOption;
}