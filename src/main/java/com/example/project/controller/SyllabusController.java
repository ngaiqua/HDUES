package com.example.project.controller;

import com.example.project.dto.SyllabusDTO;
import com.example.project.service.SyllabusService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SyllabusController {

    private final SyllabusService syllabusService;

    public SyllabusController(SyllabusService syllabusService) {
        this.syllabusService = syllabusService;
    }

    // --- Trang (MVC) ---
    @GetMapping("/syllabus")
    public String syllabusPage(Model model) {
        model.addAttribute("pageTitle", "Student's Syllabus");
        model.addAttribute("user", new DashboardController.User("Guest", ""));
        model.addAttribute("navItems", navItemsSyllabus());
        return "syllabus";
    }

    @GetMapping("/syllabus/details/{id}")
    public String syllabusDetails(@PathVariable Long id, HttpServletRequest request, Model model) {
        SyllabusDTO.SyllabusResponse syllabus = syllabusService.getSyllabusById(id);
        String qs = request.getQueryString();
        String backToSyllabusUrl = request.getContextPath() + "/syllabus" + (qs != null && !qs.isEmpty() ? "?" + qs : "");
        model.addAttribute("pageTitle", "Syllabus Details");
        model.addAttribute("user", new DashboardController.User("Guest", ""));
        model.addAttribute("navItems", navItemsSyllabus());
        model.addAttribute("syllabus", syllabus);
        model.addAttribute("backToSyllabusUrl", backToSyllabusUrl);
        return "syllabus-details";
    }

    private List<DashboardController.NavItem> navItemsSyllabus() {
        return List.of(
                new DashboardController.NavItem("/dashboard/student", "fa-house", "Home", false),
                new DashboardController.NavItem("/my-courses", "fa-book-open-reader", "My Courses", false),
                new DashboardController.NavItem("/syllabus", "fa-clipboard-list", "Syllabus", true),
                new DashboardController.NavItem("/curriculum", "fa-graduation-cap", "Curriculum", false)
        );
    }

    // --- API (REST) ---
    @GetMapping("/api/syllabus")
    @ResponseBody
    public ResponseEntity<?> getSyllabus(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String type) {
        if (q != null && !q.isBlank()) {
            return ResponseEntity.ok(syllabusService.searchSyllabus(q, type));
        }
        return ResponseEntity.ok(syllabusService.getAllSyllabus());
    }

    @GetMapping("/api/syllabus/{id}")
    @ResponseBody
    public ResponseEntity<?> getSyllabusById(@PathVariable Long id) {
        return ResponseEntity.ok(syllabusService.getSyllabusById(id));
    }

    @GetMapping("/api/syllabus/subject/{subjectId}")
    @ResponseBody
    public ResponseEntity<?> getSyllabusBySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(syllabusService.getSyllabusBySubject(subjectId));
    }

    @PostMapping("/api/syllabus")
    @ResponseBody
    public ResponseEntity<SyllabusDTO.SyllabusResponse> addSyllabus(
            @Valid @RequestBody SyllabusDTO.SyllabusRequest request) {
        SyllabusDTO.SyllabusResponse created = syllabusService.addSyllabus(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
