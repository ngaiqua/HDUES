package com.example.project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SubjectDiscussion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "subject_question_id")
    private SubjectQuestion subjectQuestion;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public SubjectQuestion getSubjectQuestion() { return subjectQuestion; }
    public void setSubjectQuestion(SubjectQuestion subjectQuestion) { this.subjectQuestion = subjectQuestion; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }
}
