package com.example.project.service;

import com.example.project.entity.Quiz;
import com.example.project.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    public Quiz saveQuiz(Quiz quiz) {
        // Business logic, such as validating start/end times, can be added here.
        return quizRepository.save(quiz);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    /**
     * Business logic to find all currently active quizzes.
     * @return A list of active quizzes.
     */
    public List<Quiz> getActiveQuizzes() {
        return quizRepository.findActiveQuizzes(LocalDateTime.now());
    }
}