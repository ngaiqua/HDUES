package com.example.project.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SubjectQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer slot;
    private String content;

    private LocalDateTime timeOpen;
    private LocalDateTime timeClose;

    @ManyToOne
    @JoinColumn(name = "class_subject_id")
    private ClassSubject classSubject;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getSlot() { return slot; }
    public void setSlot(Integer slot) { this.slot = slot; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getTimeOpen() { return timeOpen; }
    public void setTimeOpen(LocalDateTime timeOpen) { this.timeOpen = timeOpen; }

    public LocalDateTime getTimeClose() { return timeClose; }
    public void setTimeClose(LocalDateTime timeClose) { this.timeClose = timeClose; }

    public ClassSubject getClassSubject() { return classSubject; }
    public void setClassSubject(ClassSubject classSubject) { this.classSubject = classSubject; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public User getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(User updatedBy) { this.updatedBy = updatedBy; }
}
