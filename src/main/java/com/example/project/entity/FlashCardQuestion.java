package com.example.project.entity;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class FlashCardQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @ManyToOne
    @JoinColumn(name = "flashcard_id")
    private FlashCard flashCard;

    @OneToMany(mappedBy = "flashCardQuestion", cascade = CascadeType.ALL)
    private List<FlashCardAnswer> answers;

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

