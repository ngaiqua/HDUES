package com.example.project.service;

import com.example.project.dto.SyllabusDTO;
import com.example.project.entity.Syllabus;
import com.example.project.repository.SyllabusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SyllabusService {

    @Autowired
    private SyllabusRepository syllabusRepository;

    private SyllabusDTO.SyllabusResponse convertToDto(Syllabus syllabus) {
        SyllabusDTO.SyllabusResponse dto = new SyllabusDTO.SyllabusResponse();
        dto.setId(syllabus.getId());
        dto.setTitle(syllabus.getTitle());
        dto.setDescription(syllabus.getDescription());
        if (syllabus.getSubject() != null) {
            dto.setSubjectId(syllabus.getSubject().getId());
            dto.setSubjectName(syllabus.getSubject().getName());
        }
        return dto;
    }

    public List<SyllabusDTO.SyllabusResponse> getSyllabusBySubject(Long subjectId) {
        return syllabusRepository.findBySubjectId(subjectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Other CRUD methods...
}