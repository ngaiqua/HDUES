package com.example.project.dto;

public class CurriculumDTO {

    public static class CurriculumResponse {
        private Long id;
        private String curriculumName;
        private String curriculumCode;
        private String trainingLevel;
        private String programNameVi;
        private String programNameEn;
        private String generalObjectives;
        private String specificObjectives;
        private String learningOutcomes;
        private String admissionRequirements;
        private String totalKnowledgeVolume;
        private String teachingStrategy;

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
}
