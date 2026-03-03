package com.example.project.repository;

import com.example.project.entity.Curriculum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Long> {

    List<Curriculum> findByCurriculumCodeStartingWithIgnoreCase(String code);

    List<Curriculum> findByCurriculumNameStartingWithIgnoreCase(String name);
}
