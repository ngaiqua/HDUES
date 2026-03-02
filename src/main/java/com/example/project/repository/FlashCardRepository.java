package com.example.project.repository;
import com.example.project.entity.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    // Các phương thức tìm kiếm nâng cao có thể thêm ở đây
}