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
        model.addAttribute("pageTitle", "Duyệt email đăng nhập");
        model.addAttribute("basePath", "training");
        model.addAttribute("listPath", "/training/allowed-emails");
        String name = user != null && user.getFullName() != null ? user.getFullName() : (user != null ? user.getEmail() : "Guest");
        String avatar = user != null && user.getPictureUrl() != null ? user.getPictureUrl() : "https://i.pravatar.cc/150?img=1";
        model.addAttribute("user", new DashboardController.DashboardUser(name, avatar));
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
            allowedEmailService.addEmail(email, User.Role.valueOf(role), authUser != null ? authUser.getEmail() : "guest");
            ra.addFlashAttribute("message", "Đã thêm email: " + email);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/training/allowed-emails";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        allowedEmailService.deleteById(id);
        ra.addFlashAttribute("message", "Đã xóa email khỏi danh sách.");
        return "redirect:/training/allowed-emails";
    }

    @PostMapping("/import")
    public String importExcel(@RequestParam("file") MultipartFile file, RedirectAttributes ra,
                             @AuthenticationPrincipal CustomOAuth2User authUser) {
        if (file.isEmpty()) {
            ra.addFlashAttribute("error", "Vui lòng chọn file Excel.");
            return "redirect:/training/allowed-emails";
        }
        try {
            List<String> errors = allowedEmailService.importFromExcel(file, authUser != null ? authUser.getEmail() : "guest");
            if (errors.isEmpty()) {
                ra.addFlashAttribute("message", "Import thành công.");
            } else {
                ra.addFlashAttribute("importErrors", errors);
                ra.addFlashAttribute("message", "Import xong với một số lỗi.");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Lỗi đọc file: " + e.getMessage());
        }
        return "redirect:/training/allowed-emails";
    }
}
