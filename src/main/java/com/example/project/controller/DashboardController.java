package com.example.project.controller;

import com.example.project.service.CustomOAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    public static class NavItem {
        private final String url;
        private final String icon;
        private final String label;
        private final boolean active;

        public NavItem(String url, String icon, String label, boolean active) {
            this.url = url;
            this.icon = icon;
            this.label = label;
            this.active = active;
        }

        public String getUrl() { return url; }
        public String getIcon() { return icon; }
        public String getLabel() { return label; }
        public boolean isActive() { return active; }
    }

    public static class User {
        private final String name;
        private final String avatarUrl;

        public User(String name, String avatarUrl) {
            this.name = name;
            this.avatarUrl = avatarUrl;
        }

        public String getName() { return name; }
        public String getAvatarUrl() { return avatarUrl; }
    }

    @GetMapping("/{role}")
    public String getDashboardByRole(@PathVariable("role") String role,
                                    @AuthenticationPrincipal CustomOAuth2User authUser,
                                    Model model) {
        // Khi bật lại bảo mật: bỏ comment 3 dòng dưới để chặn truy cập chưa đăng nhập
        // if (authUser == null) {
        //     return "redirect:/login";
        // }
        String normalizedRole = role.toLowerCase();
        List<NavItem> navItems;
        String pageTitle;
        String avatarUrl = authUser != null && authUser.getPictureUrl() != null ? authUser.getPictureUrl() : "https://i.pravatar.cc/150?img=1";
        String fullName = authUser != null && authUser.getFullName() != null ? authUser.getFullName() : (authUser != null ? authUser.getEmail() : "Guest");

        switch (normalizedRole) {
            case "teacher":
            case "lecturer":
                pageTitle = "Lecturer";
                navItems = List.of(
                    new NavItem("/dashboard/lecturer", "fa-house", "Home", true),
                    new NavItem("/courses", "fa-book", "Courses", false),
                    new NavItem("/analytics", "fa-display", "Analytics", false)
                );
                break;
            case "student":
                pageTitle = "Student";
                navItems = List.of(
                    new NavItem("/dashboard/student", "fa-house", "Home", true),
                    new NavItem("/my-courses", "fa-book-open-reader", "My Courses", false),
                    new NavItem("/syllabus", "fa-clipboard-list", "Syllabus", false),
                    new NavItem("/curriculum", "fa-graduation-cap", "Curriculum", false)
                );
                break;
            case "admin":
                pageTitle = "System Administration";
                navItems = List.of(
                    new NavItem("/dashboard/admin", "fa-shield-halved", "Overview", true),
                    new NavItem("/admin/allowed-emails", "fa-envelope-circle-check", "Approve login emails", false),
                    new NavItem("/admin/users", "fa-users", "User management", false)
                );
                break;
            case "training":
                pageTitle = "Training Department";
                navItems = List.of(
                    new NavItem("/dashboard/training", "fa-building-columns", "Overview", true),
                    new NavItem("/training/allowed-emails", "fa-envelope-circle-check", "Approve login emails", false),
                    new NavItem("/training/classes", "fa-chalkboard-user", "Class management", false)
                );
                break;
            default:
                pageTitle = "Dashboard";
                navItems = List.of();
                break;
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("user", new User(fullName, avatarUrl));
        model.addAttribute("navItems", navItems);
        return "dashboard";
    }
}
