package com.telusko.quiz_service.service;

import com.telusko.quiz_service.dto.QuizAnswer;
import com.telusko.quiz_service.dto.QuizResultResponse;
import com.telusko.quiz_service.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizEvaluationService {

    private final QuestionService questionService;

    public QuizEvaluationService(QuestionService questionService) {
        this.questionService = questionService;
    }

    public QuizResultResponse evaluateQuiz(String category, List<QuizAnswer> userAnswers) {

        List<Question> questions = questionService.getQuestionsByCategory(category);

        int correctCount = 0;
        int totalMarks = 0;
        int maxMarks = 0;

        // Calculate total possible marks
        for (Question q : questions) {
            maxMarks += q.getMarks();
        }

        // Evaluate each submitted answer
        for (QuizAnswer ans : userAnswers) {

            for (Question q : questions) {

                if (q.getId().equals(ans.getQuestionId())) {

                    if (q.getCorrectOptionIndex() == ans.getSelectedOption()) {
                        correctCount++;
                        totalMarks += q.getMarks();
                    }
                }
            }
        }

        // Prepare final response
        return new QuizResultResponse(
                questions.size(),
                correctCount,
                totalMarks,
                maxMarks
        );
    }
}