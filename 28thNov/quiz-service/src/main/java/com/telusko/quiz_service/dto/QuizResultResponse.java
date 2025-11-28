package com.telusko.quiz_service.dto;

public class QuizResultResponse {

    private int totalQuestions;
    private int correctAnswers;
    private int totalMarksObtained;
    private int totalMarksPossible;

    public QuizResultResponse(int totalQuestions, int correctAnswers, int totalMarksObtained, int totalMarksPossible) {
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.totalMarksObtained = totalMarksObtained;
        this.totalMarksPossible = totalMarksPossible;
    }

    public QuizResultResponse() {}

    // getters & setters
    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }

    public int getTotalMarksObtained() { return totalMarksObtained; }
    public void setTotalMarksObtained(int totalMarksObtained) { this.totalMarksObtained = totalMarksObtained; }

    public int getTotalMarksPossible() { return totalMarksPossible; }
    public void setTotalMarksPossible(int totalMarksPossible) { this.totalMarksPossible = totalMarksPossible; }
}