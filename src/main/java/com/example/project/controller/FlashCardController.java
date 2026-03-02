package com.example.project.controller;

import com.example.project.entity.FlashCard;
import com.example.project.service.FlashCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flashcards")

public class FlashCardController {

    @Autowired
    private FlashCardService flashCardService;
    @PostMapping("/create")
    public FlashCard create(@RequestBody FlashCard flashCard) {
        return flashCardService.createFlashCard(flashCard);
    }
}