package com.example.project.controller;

import com.example.project.dto.CurriculumDTO;
import com.example.project.service.CurriculumService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CurriculumController {

    private final CurriculumService curriculumService;

    public CurriculumController(CurriculumService curriculumService) {
        this.curriculumService = curriculumService;
    }

    @GetMapping("/curriculum")
    public String curriculumPage(Model model) {
        model.addAttribute("pageTitle", "Curriculum");
        model.addAttribute("user", new DashboardController.User("Guest", ""));
        model.addAttribute("navItems", navItemsCurriculum());
        return "curriculum";
    }

    @GetMapping("/curriculum/details/{id}")
    public String curriculumDetails(@PathVariable Long id, HttpServletRequest request, Model model) {
        CurriculumDTO.CurriculumResponse curriculum = curriculumService.getCurriculumById(id);
        String qs = request.getQueryString();
        String backToCurriculumUrl = request.getContextPath() + "/curriculum" + (qs != null && !qs.isEmpty() ? "?" + qs : "");
        model.addAttribute("pageTitle", "Curriculum Details");
        model.addAttribute("user", new DashboardController.User("Guest", ""));
        model.addAttribute("navItems", navItemsCurriculum());
        model.addAttribute("curriculum", curriculum);
        model.addAttribute("backToCurriculumUrl", backToCurriculumUrl);
        return "curriculum-details";
    }

    private List<DashboardController.NavItem> navItemsCurriculum() {
        return List.of(
                new DashboardController.NavItem("/dashboard/student", "fa-house", "Home", false),
                new DashboardController.NavItem("/my-courses", "fa-book-open-reader", "My Courses", false),
                new DashboardController.NavItem("/syllabus", "fa-clipboard-list", "Syllabus", false),
                new DashboardController.NavItem("/curriculum", "fa-graduation-cap", "Curriculum", true)
        );
    }

    @GetMapping("/api/curriculum")
    @ResponseBody
    public ResponseEntity<?> getCurriculum(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String type) {
        if (q != null && !q.isBlank()) {
            return ResponseEntity.ok(curriculumService.searchCurriculum(q, type));
        }
        return ResponseEntity.ok(curriculumService.getAllCurriculum());
    }

    @GetMapping("/api/curriculum/{id}")
    @ResponseBody
    public ResponseEntity<?> getCurriculumById(@PathVariable Long id) {
        return ResponseEntity.ok(curriculumService.getCurriculumById(id));
    }
}
