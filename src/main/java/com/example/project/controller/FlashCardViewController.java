package com.example.project.controller;

import com.example.project.repository.FlashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FlashCardViewController {

    @Autowired
    private FlashCardRepository repo;

    @GetMapping("/flashcards")
    public String viewAll(Model model) {
        // Lấy dữ liệu từ Postgres/H2 để đổ ra giao diện
        model.addAttribute("flashcards", repo.findAll());
        return "layout/flashcard"; // Nó sẽ tìm file index.html trong thư mục templates
    }
    // Trong FlashCardViewController.java
    @GetMapping("/flashcards/new")
    public String showCreateForm() {
    return "layout/create-flashcard"; // Đường dẫn đến file HTML vừa tạo
}
}