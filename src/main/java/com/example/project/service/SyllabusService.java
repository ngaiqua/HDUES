package com.example.project.service;

import com.example.project.dto.SyllabusDTO;
import com.example.project.entity.Syllabus;
import com.example.project.entity.Subject;
import com.example.project.repository.SyllabusRepository;
import com.example.project.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SyllabusService {

    @Autowired
    private SyllabusRepository syllabusRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private SyllabusDTO.SyllabusResponse convertToDto(Syllabus syllabus) {
        SyllabusDTO.SyllabusResponse dto = new SyllabusDTO.SyllabusResponse();
        dto.setId(syllabus.getId());
        dto.setCredits(syllabus.getCredits());
        dto.setTheoryPeriods(syllabus.getTheoryPeriods());
        dto.setPracticePeriods(syllabus.getPracticePeriods());
        dto.setSelfStudyPeriods(syllabus.getSelfStudyPeriods());
        dto.setCourseType(syllabus.getCourseType());
        dto.setPrerequisite(syllabus.getPrerequisite());
        dto.setTeachingLanguage(syllabus.getTeachingLanguage());
        dto.setSummary(syllabus.getSummary());
        dto.setMainTextbook(syllabus.getMainTextbook());
        dto.setReferenceMaterials(syllabus.getReferenceMaterials());
        dto.setStudentTasks(syllabus.getStudentTasks());
        if (syllabus.getSubject() != null) {
            dto.setSubjectId(syllabus.getSubject().getId());
            dto.setSubjectCode(syllabus.getSubject().getCode());
            dto.setSubjectName(syllabus.getSubject().getName());
        }
        return dto;
    }

    public List<SyllabusDTO.SyllabusResponse> getAllSyllabus() {
        return syllabusRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SyllabusDTO.SyllabusResponse> getSyllabusBySubject(Long subjectId) {
        return syllabusRepository.findBySubject_Id(subjectId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public SyllabusDTO.SyllabusResponse getSyllabusById(Long id) {
        Long sid = Objects.requireNonNull(id, "syllabus id is required");
        Syllabus syllabus = syllabusRepository.findById(sid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Syllabus not found"));
        return convertToDto(syllabus);
    }

    /** Chuẩn hóa bỏ dấu + chữ thường (toan → khớp Toán). */
    private static String norm(String s) {
        if (s == null || s.isEmpty()) return s;
        String n = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return n.replace('\u0111', 'd').replace('\u0110', 'D').toLowerCase();
    }

    /** type: "code" = DB; "name"/khác = filter theo name hoặc code+name, bắt đầu bằng (không dấu). */
    public List<SyllabusDTO.SyllabusResponse> searchSyllabus(String keyword, String type) {
        if (keyword == null || keyword.isBlank()) return getAllSyllabus();
        String k = keyword.trim();
        if ("code".equalsIgnoreCase(type)) {
            return syllabusRepository.findBySubject_CodeStartingWithIgnoreCase(k).stream()
                    .map(this::convertToDto).collect(Collectors.toList());
        }
        String nk = norm(k);
        List<Syllabus> list = syllabusRepository.findAll().stream()
                .filter(s -> {
                    if (s.getSubject() == null) return false;
                    String name = s.getSubject().getName();
                    if ("name".equalsIgnoreCase(type))
                        return name != null && norm(name).startsWith(nk);
                    String code = s.getSubject().getCode();
                    return (code != null && norm(code).startsWith(nk)) || (name != null && norm(name).startsWith(nk));
                })
                .collect(Collectors.toList());
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public SyllabusDTO.SyllabusResponse addSyllabus(SyllabusDTO.SyllabusRequest request) {
        Long subjectId = Objects.requireNonNull(request.getSubjectId(), "subjectId is required");
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + subjectId));

        Syllabus syllabus = new Syllabus();
        syllabus.setSubject(subject);
        syllabus.setCredits(request.getCredits());
        syllabus.setTheoryPeriods(request.getTheoryPeriods());
        syllabus.setPracticePeriods(request.getPracticePeriods());
        syllabus.setSelfStudyPeriods(request.getSelfStudyPeriods());
        syllabus.setCourseType(request.getCourseType());
        syllabus.setPrerequisite(request.getPrerequisite());
        syllabus.setTeachingLanguage(request.getTeachingLanguage());
        syllabus.setSummary(request.getSummary());
        syllabus.setMainTextbook(request.getMainTextbook());
        syllabus.setReferenceMaterials(request.getReferenceMaterials());
        syllabus.setStudentTasks(request.getStudentTasks());

        Syllabus saved = syllabusRepository.save(syllabus);
        return convertToDto(saved);
    }
}