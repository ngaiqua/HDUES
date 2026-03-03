package com.example.project.service;

import com.example.project.dto.SubjectDTO;
import com.example.project.entity.Subject;
import com.example.project.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    private SubjectDTO.SubjectResponse convertToDto(Subject subject) {
        SubjectDTO.SubjectResponse dto = new SubjectDTO.SubjectResponse();
        dto.setId(subject.getId());
        dto.setCode(subject.getCode());
        dto.setName(subject.getName());
        dto.setDescription(subject.getDescription());
        return dto;
    }

    public List<SubjectDTO.SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SubjectDTO.SubjectResponse addSubject(SubjectDTO.SubjectRequest request) {
        Subject subject = new Subject();
        subject.setCode(request.getCode().trim());
        subject.setName(request.getName().trim());
        subject.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        Subject saved = subjectRepository.save(subject);
        return convertToDto(saved);
    }
}