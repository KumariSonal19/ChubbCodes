package com.telusko.question_service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.telusko.question_service.model.Question;


public interface QuestionRepository extends MongoRepository<Question,String>{

	List<Question> findByCategory(String category);

	@Aggregation(pipeline = {
		    "{ $match: { category: ?0 } }",
		    "{ $sample: { size: ?1 } }",
		    "{ $project: { _id: 1 } }"
		})
	List<String> findRandomQuestionByCategory(String category, int numQ);

}