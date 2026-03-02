package com.example.project.service;

import com.example.project.entity.FlashCard;
import com.example.project.repository.FlashCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlashCardService {

    @Autowired
    private FlashCardRepository flashCardRepository;

    @Transactional
    public FlashCard createFlashCard(FlashCard flashCard) {
        // 1. Kiểm tra nếu có danh sách câu hỏi
        if (flashCard.getQuestions() != null) {
            flashCard.getQuestions().forEach(question -> {
                
                // 2. Gán FlashCard cha cho từng Question
                question.setFlashCard(flashCard);
                
                // 3. Gán Question cho từng Answer tương ứng
                if (question.getAnswers() != null) {
                    question.getAnswers().forEach(answer -> {
                        answer.setFlashCardQuestion(question);
                    });
                }
            });
        }
        // 4. Lưu 1 lần duy nhất (nhờ có CascadeType.ALL đã cấu hình ở Entity)
        return flashCardRepository.save(flashCard);
    }
}