package com.example.project.repository;

import com.example.project.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    /**
     * Best Practice: Using JPQL to create a type-safe query.
     * This query finds all assignments for a specific class, which is a very common use case.
     * Indexing the 'class_subject_id' column in the database is recommended for performance.
     *
     * @param classSubjectId The ID of the class_subject entity.
     * @return A list of assignments for the given class.
     */
    @Query("SELECT a FROM Assignment a WHERE a.classSubject.id = :classSubjectId ORDER BY a.dueDate ASC")
    List<Assignment> findByClassSubjectId(Long classSubjectId);
}