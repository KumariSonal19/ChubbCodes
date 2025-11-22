package com.MongoSpring.MongoSpring.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
	
	@Id
	private Integer rno;
	
	private String name;
	
	private String address;
}
