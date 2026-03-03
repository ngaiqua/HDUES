package com.example.project.controller;

import com.example.project.entity.User;
import com.example.project.service.AllowedEmailService;
import com.example.project.service.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/training/allowed-emails")
public class TrainingAllowedEmailController {

    private final AllowedEmailService allowedEmailService;

    public TrainingAllowedEmailController(AllowedEmailService allowedEmailService) {
        this.allowedEmailService = allowedEmailService;
    }

    @GetMapping
    public String list(Model model, @AuthenticationPrincipal CustomOAuth2User user) {
        model.addAttribute("allowedEmails", allowedEmailService.findAll());
        model.addAttribute("pageTitle", "Approve login emails");
        model.addAttribute("basePath", "training");
        model.addAttribute("listPath", "/training/allowed-emails");
        String name = user != null && user.getFullName() != null ? user.getFullName() : (user != null ? user.getEmail() : "Guest");
        String avatar = user != null && user.getPictureUrl() != null ? user.getPictureUrl() : "https://i.pravatar.cc/150?img=1";
        model.addAttribute("user", new DashboardController.User(name, avatar));
        model.addAttribute("currentUserEmail", user != null ? user.getEmail() : null);
        model.addAttribute("navItems", List.of(
            new DashboardController.NavItem("/dashboard/training", "fa-building-columns", "Tổng quan", false),
            new DashboardController.NavItem("/training/allowed-emails", "fa-envelope-circle-check", "Duyệt email đăng nhập", true),
            new DashboardController.NavItem("/training/classes", "fa-chalkboard-user", "Quản lý lớp", false)
        ));
        return "allowed-emails";
    }

    @PostMapping("/add")
    public String add(@RequestParam String email, @RequestParam(defaultValue = "STUDENT") String role,
                      RedirectAttributes ra, @AuthenticationPrincipal CustomOAuth2User authUser) {
        try {
            User.Role parsedRole;
            try {
                parsedRole = User.Role.valueOf(role);
            } catch (IllegalArgumentException ex) {
                parsedRole = User.Role.STUDENT;
            }
            if (parsedRole != User.Role.STUDENT && parsedRole != User.Role.LECTURER) {
                parsedRole = User.Role.STUDENT;
            }
            allowedEmailService.addEmail(email, parsedRole, authUser != null ? authUser.getEmail() : "guest");
            ra.addFlashAttribute("message", "Email added: " + email + " with role " + parsedRole.name());
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/training/allowed-emails";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes ra,
                         @AuthenticationPrincipal CustomOAuth2User authUser) {
        String currentEmail = authUser != null ? authUser.getEmail() : null;
        if (currentEmail != null) {
            var allowedOpt = allowedEmailService.findById(id);
            if (allowedOpt.isPresent() && currentEmail.equalsIgnoreCase(allowedOpt.get().getEmail())) {
                ra.addFlashAttribute("error", "You cannot delete your own email.");
                return "redirect:/training/allowed-emails";
            }
        }
        try {
            allowedEmailService.deleteByIdForTraining(id);
            ra.addFlashAttribute("message", "Email has been removed from the list.");
        } catch (IllegalArgumentException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }
        return "redirect:/training/allowed-emails";
    }

    @PostMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file, RedirectAttributes ra,
                             @AuthenticationPrincipal CustomOAuth2User authUser) {
        if (file.isEmpty()) {
            ra.addFlashAttribute("error", "Please select an Excel file.");
            return "redirect:/training/allowed-emails";
        }
        try {
            List<String> errors = allowedEmailService.importFromExcelForTraining(file, authUser != null ? authUser.getEmail() : "guest");
            if (errors.isEmpty()) {
                ra.addFlashAttribute("message", "Import successfully completed.");
            } else {
                ra.addFlashAttribute("importErrors", errors);
                ra.addFlashAttribute("message", "Import completed with some errors.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error reading file: " + e.getMessage());
        }
        return "redirect:/training/allowed-emails";
    }
}
