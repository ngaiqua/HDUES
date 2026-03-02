package com.example.project.entity;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class FlashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // TỐI ƯU: Thêm quan hệ xuống Question để lưu lồng nhau (Cascade)
    @OneToMany(mappedBy = "flashCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FlashCardQuestion> questions = new ArrayList<>();

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<FlashCardQuestion> getQuestions() { return questions; }
    public void setQuestions(List<FlashCardQuestion> questions) { this.questions = questions; }
}