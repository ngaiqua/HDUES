package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/classroom")
public class ClassroomController {

    @GetMapping("/dashboard")
    public String getDashboard() {
        // This will resolve to src/main/resources/templates/pages/classroom/dashboard.html
        return "pages/classroom/dashboard";
    }
}