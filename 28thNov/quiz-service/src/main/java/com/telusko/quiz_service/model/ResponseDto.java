package com.telusko.quiz_service.model;

import org.springframework.data.annotation.Id;

public class ResponseDto {
	@Id
    private String id;
	private String rightAnswer;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRightAnswer() {
		return rightAnswer;
	}
	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
}