package com.telusko.quiz_service.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telusko.quiz_service.client.QuestionClient;
import com.telusko.quiz_service.model.QuestionWrapper;
import com.telusko.quiz_service.model.Quiz;
import com.telusko.quiz_service.model.ResponseDto;
import com.telusko.quiz_service.repository.QuizRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class QuizService {

    private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private static final String QUESTIONS_CB = "questionsServiceCb";

    @Autowired
    QuizRepository quizRepo;

    @Autowired
    QuestionClient questionInterface;

    @CircuitBreaker(name = QUESTIONS_CB, fallbackMethod = "fallbackCreateQuiz")
    public ResponseEntity<Quiz> createQuiz(String category, int numQ, String title) {
        List<String> questions = questionInterface.getQuestionForQuiz(category, numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        return new ResponseEntity<>(quizRepo.save(quiz), HttpStatus.CREATED);
    }

    public ResponseEntity<Quiz> fallbackCreateQuiz(String category, int numQ, String title, Throwable t) {
        logger.warn("createQuiz fallback triggered for category={}, numQ={}, title={}. Reason: {}",
                category, numQ, title, t.toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }

    @CircuitBreaker(name = QUESTIONS_CB, fallbackMethod = "fallbackGetQuizQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuiz(String quizId) {

        Optional<Quiz> quizOpt = quizRepo.findById(quizId);

        if (quizOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Quiz quiz = quizOpt.get();
        return questionInterface.getQuestions(quiz.getQuestionIds());
    }

    public ResponseEntity<List<QuestionWrapper>> fallbackGetQuizQuestions(String quizId, Throwable t) {
        logger.warn("getQuiz fallback triggered for quizId={}. Reason: {}", quizId, t.toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(List.of());
    }

    @CircuitBreaker(name = QUESTIONS_CB, fallbackMethod = "fallbackSubmitQuiz")
    public ResponseEntity<Integer> submitQuiz(String quizId, List<ResponseDto> responses) {
        Integer score = questionInterface.getScore(responses).getBody();
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

   
    public ResponseEntity<Integer> fallbackSubmitQuiz(String quizId, List<ResponseDto> responses, Throwable t) {
        logger.warn("submitQuiz fallback triggered for quizId={}. Reason: {}", quizId, t.toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(-1);
    }
}
