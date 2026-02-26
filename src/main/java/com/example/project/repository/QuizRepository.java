package com.example.project.repository;

import com.example.project.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    /**
     * Finds all quizzes associated with a specific assignment.
     * This is useful for getting quiz details when you have the parent assignment.
     *
     * @param assignmentId The ID of the assignment.
     * @return A list of quizzes (usually one).
     */
    List<Quiz> findByAssignmentId(Long assignmentId);

    /**
     * Best Practice: Querying based on time ranges is crucial for features like "upcoming quizzes".
     * This query finds all quizzes that are currently active (start_time has passed, end_time has not).
     * Using TIMESTAMPTZ for date/time columns in PostgreSQL is highly recommended to handle time zones correctly.
     *
     * @param now The current date and time.
     * @return A list of currently active quizzes.
     */
    @Query("SELECT q FROM Quiz q WHERE q.startTime <= :now AND q.endTime >= :now")
    List<Quiz> findActiveQuizzes(LocalDateTime now);
}