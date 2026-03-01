package com.example.project.controller;

import com.example.project.dto.SubjectDTO;
import com.example.project.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }


    @GetMapping
    public ResponseEntity<List<SubjectDTO.SubjectResponse>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }


    @PostMapping
    public ResponseEntity<SubjectDTO.SubjectResponse> addSubject(@Valid @RequestBody SubjectDTO.SubjectRequest request) {
        SubjectDTO.SubjectResponse created = subjectService.addSubject(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
