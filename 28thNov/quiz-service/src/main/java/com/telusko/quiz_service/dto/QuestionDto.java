package com.telusko.quiz_service.dto;

import java.util.List;

public class QuestionDto {
    private String id;
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;
    private String category;
    private int marks;

    // getters + setters
}