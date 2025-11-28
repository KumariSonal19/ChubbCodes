package com.telusko.quiz_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuizSubmissionRequest {
    private String category;
    private List<QuizAnswer> answers;
}