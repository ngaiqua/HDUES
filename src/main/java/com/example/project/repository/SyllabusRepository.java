package com.example.project.repository;

import com.example.project.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SyllabusRepository extends JpaRepository<Syllabus, Long> {
    List<Syllabus> findBySubject_Id(Long subjectId);

    /** Bắt đầu bằng: "p" → code bắt đầu "p", "pl" → bắt đầu "pl". */
    List<Syllabus> findBySubject_CodeStartingWithIgnoreCase(String code);

    @Query("SELECT s FROM Syllabus s WHERE LOWER(s.subject.name) LIKE CONCAT(LOWER(:keyword), '%')")
    List<Syllabus> findBySubject_NameStartingWithIgnoreCase(@Param("keyword") String keyword);

    @Query("SELECT s FROM Syllabus s WHERE LOWER(s.subject.code) LIKE CONCAT(LOWER(:keyword), '%') OR LOWER(s.subject.name) LIKE CONCAT(LOWER(:keyword), '%')")
    List<Syllabus> findBySubject_CodeOrNameStartingWith(@Param("keyword") String keyword);
}