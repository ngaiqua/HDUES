package com.example.project.controller;

import com.example.project.entity.AllowedEmail;
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
@RequestMapping("/admin/allowed-emails")
public class AllowedEmailController {

    private final AllowedEmailService allowedEmailService;

    public AllowedEmailController(AllowedEmailService allowedEmailService) {
        this.allowedEmailService = allowedEmailService;
    }

    @GetMapping
    public String list(Model model, @AuthenticationPrincipal CustomOAuth2User user) {
        model.addAttribute("allowedEmails", allowedEmailService.findAll());
        model.addAttribute("pageTitle", "Approve login emails");
        model.addAttribute("basePath", "admin");
        model.addAttribute("listPath", "/admin/allowed-emails");
        String name = user != null && user.getFullName() != null ? user.getFullName() : (user != null ? user.getEmail() : "Guest");
        String avatar = user != null && user.getPictureUrl() != null ? user.getPictureUrl() : "https://i.pravatar.cc/150?img=1";
        model.addAttribute("user", new DashboardController.User(name, avatar));
        model.addAttribute("currentUserEmail", user != null ? user.getEmail() : null);
        model.addAttribute("navItems", List.of(
            new DashboardController.NavItem("/dashboard/admin", "fa-shield-halved", "Overview", false),
            new DashboardController.NavItem("/admin/allowed-emails", "fa-envelope-circle-check", "Approve login emails", true),
            new DashboardController.NavItem("/admin/users", "fa-users", "User management", false)
        ));
        return "allowed-emails";
    }

    @PostMapping("/add")
    public String add(@RequestParam String email, @RequestParam(defaultValue = "STUDENT") String role,
                     RedirectAttributes ra, @AuthenticationPrincipal CustomOAuth2User authUser) {
        try {
            allowedEmailService.addEmail(email, User.Role.valueOf(role), authUser != null ? authUser.getEmail() : "guest");
            ra.addFlashAttribute("message", "Email added: " + email);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/allowed-emails";
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
                return "redirect:/admin/allowed-emails";
            }
        }
        allowedEmailService.deleteById(id);
        ra.addFlashAttribute("message", "Email has been removed from the list.");
        return "redirect:/admin/allowed-emails";
    }

    @PostMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file, RedirectAttributes ra,
                             @AuthenticationPrincipal CustomOAuth2User authUser) {
        if (file.isEmpty()) {
            ra.addFlashAttribute("error", "Please select an Excel file.");
            return "redirect:/admin/allowed-emails";
        }
        try {
            List<String> errors = allowedEmailService.importFromExcel(file, authUser != null ? authUser.getEmail() : "guest");
            if (errors.isEmpty()) {
                ra.addFlashAttribute("message", "Import successfully completed.");
            } else {
                ra.addFlashAttribute("importErrors", errors);
                ra.addFlashAttribute("message", "Import completed with some errors.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Error reading file: " + e.getMessage());
        }
        return "redirect:/admin/allowed-emails";
    }
}
