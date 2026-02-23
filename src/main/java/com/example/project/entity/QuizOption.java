package com.example.project.entity;

import jakarta.persistence.*;

@Entity
public class QuizOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String quizAnswer;

    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "quizQuestion_id")
    private QuizQuestion quizQuestion;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuizAnswer() { return quizAnswer; }
    public void setQuizAnswer(String quizAnswer) { this.quizAnswer = quizAnswer; }

    public Boolean getIsCorrect() { return isCorrect; }
    public void setIsCorrect(Boolean isCorrect) { this.isCorrect = isCorrect; }

    public QuizQuestion getQuizQuestion() { return quizQuestion; }
    public void setQuizQuestion(QuizQuestion quizQuestion) { this.quizQuestion = quizQuestion; }
}
