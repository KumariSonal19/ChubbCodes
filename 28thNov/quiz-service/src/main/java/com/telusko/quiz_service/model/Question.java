package com.telusko.quiz_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;  // <-- THIS WAS MISSING
import java.util.List;

@Document(collection = "questions")
public class Question {

    @Id
    private String id;

    private String questionText;
    private List<String> options;
    private int correctOptionIndex;
    private String category;
    private int marks;

    public Question() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public List<String> getOptions() { return options; }
    public void setOptions(List<String> options) { this.options = options; }

    public int getCorrectOptionIndex() { return correctOptionIndex; }
    public void setCorrectOptionIndex(int correctOptionIndex) { this.correctOptionIndex = correctOptionIndex; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getMarks() { return marks; }
    public void setMarks(int marks) { this.marks = marks; }
}