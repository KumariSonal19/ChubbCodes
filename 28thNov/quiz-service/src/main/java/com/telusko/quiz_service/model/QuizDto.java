package com.telusko.quiz_service.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data

public class QuizDto {

	String categoryName;
	Integer numQ;
	String title;
}