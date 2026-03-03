package com.example.project.service;

import com.example.project.dto.CurriculumDTO;
import com.example.project.entity.Curriculum;
import com.example.project.repository.CurriculumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.Normalizer;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CurriculumService {

    @Autowired
    private CurriculumRepository curriculumRepository;

    private CurriculumDTO.CurriculumResponse convertToDto(Curriculum c) {
        CurriculumDTO.CurriculumResponse dto = new CurriculumDTO.CurriculumResponse();
        dto.setId(c.getId());
        dto.setCurriculumName(c.getCurriculumName());
        dto.setCurriculumCode(c.getCurriculumCode());
        dto.setTrainingLevel(c.getTrainingLevel());
        dto.setProgramNameVi(c.getProgramNameVi());
        dto.setProgramNameEn(c.getProgramNameEn());
        dto.setGeneralObjectives(c.getGeneralObjectives());
        dto.setSpecificObjectives(c.getSpecificObjectives());
        dto.setLearningOutcomes(c.getLearningOutcomes());
        dto.setAdmissionRequirements(c.getAdmissionRequirements());
        dto.setTotalKnowledgeVolume(c.getTotalKnowledgeVolume());
        dto.setTeachingStrategy(c.getTeachingStrategy());
        return dto;
    }

    public List<CurriculumDTO.CurriculumResponse> getAllCurriculum() {
        return curriculumRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CurriculumDTO.CurriculumResponse getCurriculumById(Long id) {
        Long cid = Objects.requireNonNull(id, "curriculum id is required");
        Curriculum curriculum = curriculumRepository.findById(cid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curriculum not found"));
        return convertToDto(curriculum);
    }

    private static String norm(String s) {
        if (s == null || s.isEmpty()) return s;
        String n = Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return n.replace('\u0111', 'd').replace('\u0110', 'D').toLowerCase();
    }

    /** type: "code" = by curriculumCode; "name" or other = by curriculumName or programName (starts with, no accent). */
    public List<CurriculumDTO.CurriculumResponse> searchCurriculum(String keyword, String type) {
        if (keyword == null || keyword.isBlank()) return getAllCurriculum();
        String k = keyword.trim();
        if ("code".equalsIgnoreCase(type)) {
            return curriculumRepository.findByCurriculumCodeStartingWithIgnoreCase(k).stream()
                    .map(this::convertToDto).collect(Collectors.toList());
        }
        String nk = norm(k);
        List<Curriculum> list = curriculumRepository.findAll().stream()
                .filter(c -> {
                    if ("name".equalsIgnoreCase(type)) {
                        String name = c.getCurriculumName();
                        return name != null && norm(name).startsWith(nk);
                    }
                    String name = c.getCurriculumName();
                    String code = c.getCurriculumCode();
                    String pVi = c.getProgramNameVi();
                    String pEn = c.getProgramNameEn();
                    return (code != null && norm(code).startsWith(nk))
                            || (name != null && norm(name).startsWith(nk))
                            || (pVi != null && norm(pVi).startsWith(nk))
                            || (pEn != null && norm(pEn).startsWith(nk));
                })
                .collect(Collectors.toList());
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
