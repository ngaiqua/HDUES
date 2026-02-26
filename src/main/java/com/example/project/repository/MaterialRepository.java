package com.example.project.repository;

import com.example.project.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {

    /**
     * Best Practice: A common requirement is to get all materials for a specific class.
     * This JPQL query is clean and directly maps to the business need.
     * An index on 'class_subject_id' is crucial for good performance as the number of materials grows.
     *
     * @param classSubjectId The ID of the class_subject entity.
     * @return A list of materials for the given class, ordered by creation date.
     */
    @Query("SELECT m FROM Material m WHERE m.classSubject.id = :classSubjectId ORDER BY m.createdAt DESC")
    List<Material> findByClassSubjectId(Long classSubjectId);
}