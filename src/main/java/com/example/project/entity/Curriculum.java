package com.example.project.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "curriculum")
public class Curriculum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String curriculumName;   // Tên Ngành đào tạo
    @Column(length = 50)
    private String curriculumCode;  // Mã Ngành đào tạo
    @Column(length = 100)
    private String trainingLevel;       // Trình độ đào tạo
    @Column(length = 500)
    private String programNameVi;       // Tên chương trình (Tiếng Việt)
    @Column(length = 500)
    private String programNameEn;       // Tên chương trình (Tiếng Anh)

    @Column(length = 2000)
    private String generalObjectives;   // Mục tiêu chung
    @Column(length = 2000)
    private String specificObjectives;  // Mục tiêu cụ thể
    @Column(length = 2000)
    private String learningOutcomes;    // Chuẩn đầu ra
    @Column(length = 2000)
    private String admissionRequirements;      // Chuẩn đầu vào
    @Column(length = 2000)
    private String totalKnowledgeVolume; // Khối lượng kiến thức toàn khóa
    @Column(length = 4000)
    private String teachingStrategy;    // Chiến lược dạy học


    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCurriculumName() { return curriculumName; }
    public void setCurriculumName(String curriculumName) { this.curriculumName = curriculumName; }

    public String getCurriculumCode() { return curriculumCode; }
    public void setCurriculumCode(String curriculumCode) { this.curriculumCode = curriculumCode; }

    public String getTrainingLevel() { return trainingLevel; }
    public void setTrainingLevel(String trainingLevel) { this.trainingLevel = trainingLevel; }

    public String getProgramNameVi() { return programNameVi; }
    public void setProgramNameVi(String programNameVi) { this.programNameVi = programNameVi; }

    public String getProgramNameEn() { return programNameEn; }
    public void setProgramNameEn(String programNameEn) { this.programNameEn = programNameEn; }

    public String getGeneralObjectives() { return generalObjectives; }
    public void setGeneralObjectives(String generalObjectives) { this.generalObjectives = generalObjectives; }

    public String getSpecificObjectives() { return specificObjectives; }
    public void setSpecificObjectives(String specificObjectives) { this.specificObjectives = specificObjectives; }

    public String getLearningOutcomes() { return learningOutcomes; }
    public void setLearningOutcomes(String learningOutcomes) { this.learningOutcomes = learningOutcomes; }

    public String getAdmissionRequirements() { return admissionRequirements; }
    public void setAdmissionRequirements(String admissionRequirements) { this.admissionRequirements = admissionRequirements; }

    public String getTotalKnowledgeVolume() { return totalKnowledgeVolume; }
    public void setTotalKnowledgeVolume(String totalKnowledgeVolume) { this.totalKnowledgeVolume = totalKnowledgeVolume; }

    public String getTeachingStrategy() { return teachingStrategy; }
    public void setTeachingStrategy(String teachingStrategy) { this.teachingStrategy = teachingStrategy; }

}
