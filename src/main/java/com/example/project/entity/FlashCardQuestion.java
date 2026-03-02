package com.example.project.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class FlashCardQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @ManyToOne
    @JoinColumn(name = "flashcard_id")
    private FlashCard flashCard;

    // TỐI ƯU: CascadeType.ALL giúp tự động lưu Answers khi lưu Question
    @OneToMany(mappedBy = "flashCardQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlashCardAnswer> answers = new ArrayList<>();

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public FlashCard getFlashCard() { return flashCard; }
    public void setFlashCard(FlashCard flashCard) { this.flashCard = flashCard; }

    public List<FlashCardAnswer> getAnswers() { return answers; }
    public void setAnswers(List<FlashCardAnswer> answers) { this.answers = answers; }
}