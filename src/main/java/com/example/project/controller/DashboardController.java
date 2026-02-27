package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    // A simple NavItem class to hold sidebar menu data
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

    // A simple User class for demo purposes
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
    public String getDashboardByRole(@PathVariable("role") String role, Model model) {
        String normalizedRole = role.toLowerCase();
        List<NavItem> navItems;

        switch (normalizedRole) {
            case "teacher":
            case "lecturer":
                model.addAttribute("pageTitle", "Teacher's Workspace");
                model.addAttribute("user", new User("Dr. Emily Carter", "https://i.pravatar.cc/150?img=11"));
                navItems = List.of(
                    new NavItem("/dashboard/teacher", "fa-house", "Home", true),
                    new NavItem("/courses", "fa-book", "My Courses", false),
                    new NavItem("/analytics", "fa-display", "Dashboard", false)
                );
                break;
            case "student":
                model.addAttribute("pageTitle", "Student's Dashboard");
                model.addAttribute("user", new User("Alex Johnson", "https://i.pravatar.cc/150?img=32"));
                navItems = List.of(
                    new NavItem("/dashboard/student", "fa-house", "Home", true),
                    new NavItem("/my-courses", "fa-book-open-reader", "My Courses", false),
                    new NavItem("/my-grades", "fa-graduation-cap", "My Grades", false)
                );
                break;
            case "admin":
                model.addAttribute("pageTitle", "System Administration");
                model.addAttribute("user", new User("Admin User", "https://i.pravatar.cc/150?img=1"));
                navItems = List.of(
                    new NavItem("/dashboard/admin", "fa-shield-halved", "Overview", true),
                    new NavItem("/admin/users", "fa-users", "User Management", false)
                );
                break;
            case "training":
            case "training-dept":
                model.addAttribute("pageTitle", "Training Department");
                model.addAttribute("user", new User("Training Officer", "https://i.pravatar.cc/150?img=5"));
                navItems = List.of(
                    new NavItem("/dashboard/training", "fa-building-columns", "Dashboard", true),
                    new NavItem("/training/classes", "fa-chalkboard-user", "Class Management", false)
                );
                break;
            default:
                model.addAttribute("pageTitle", "Dashboard");
                model.addAttribute("user", new User("Guest", ""));
                navItems = List.of();
                break;
        }

        model.addAttribute("navItems", navItems);
        
        // All roles will use the same dashboard view
        return "dashboard";
    }
}