package com.example.project.controller;

import com.example.project.dto.SubjectDTO;
import com.example.project.dto.UserDTO;
import com.example.project.service.SubjectService;
import com.example.project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin Management", description = "APIs for managing core entities like Users and Subjects")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Operation(summary = "Get all users", description = "Returns a list of all users in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO.UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Get all subjects", description = "Returns a list of all subjects.")
    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectDTO.SubjectResponse>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    // Other endpoints for POST, PUT, DELETE would go here
}