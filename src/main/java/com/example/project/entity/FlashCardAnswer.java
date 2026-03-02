package com.example.project.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "flashcard_answer")
public class FlashCardAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;

    // TỐI ƯU: Thêm cờ đánh dấu đáp án đúng cho Multiple Choice
    private boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "flashcard_question_id")
    private FlashCardQuestion flashCardQuestion;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public boolean isCorrect() { return isCorrect; }
    public void setCorrect(boolean correct) { isCorrect = correct; }

    public FlashCardQuestion getFlashCardQuestion() { return flashCardQuestion; }
    public void setFlashCardQuestion(FlashCardQuestion flashCardQuestion) { this.flashCardQuestion = flashCardQuestion; }
}