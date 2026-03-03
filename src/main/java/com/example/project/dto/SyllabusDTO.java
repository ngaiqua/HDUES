package com.example.project.dto;

import jakarta.validation.constraints.NotNull;

public class SyllabusDTO {

    public static class SyllabusRequest {
        @NotNull(message = "Subject ID is mandatory")
        private Long subjectId;

        private Integer credits;           // Số tín chỉ
        private Integer theoryPeriods;     // Lí thuyết (tiết)
        private Integer practicePeriods;   // Thực hành (tiết)
        private Integer selfStudyPeriods;  // Tự học (tiết)
        private String courseType;         // Loại học phần
        private String prerequisite;      // Học phần tiên quyết
        private String teachingLanguage;   // Ngôn ngữ giảng dạy
        private String summary;            // Tóm tắt
        private String mainTextbook;       // Giáo trình chính
        private String referenceMaterials; // Tài liệu tham khảo
        private String studentTasks;       // Nhiệm vụ của sinh viên
        private String objectives;         // Mục tiêu
        private String facilities;         // Cơ sở vật chất

        public Long getSubjectId() { return subjectId; }
        public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
        public Integer getCredits() { return credits; }
        public void setCredits(Integer credits) { this.credits = credits; }
        public Integer getTheoryPeriods() { return theoryPeriods; }
        public void setTheoryPeriods(Integer theoryPeriods) { this.theoryPeriods = theoryPeriods; }
        public Integer getPracticePeriods() { return practicePeriods; }
        public void setPracticePeriods(Integer practicePeriods) { this.practicePeriods = practicePeriods; }
        public Integer getSelfStudyPeriods() { return selfStudyPeriods; }
        public void setSelfStudyPeriods(Integer selfStudyPeriods) { this.selfStudyPeriods = selfStudyPeriods; }
        public String getCourseType() { return courseType; }
        public void setCourseType(String courseType) { this.courseType = courseType; }
        public String getPrerequisite() { return prerequisite; }
        public void setPrerequisite(String prerequisite) { this.prerequisite = prerequisite; }
        public String getTeachingLanguage() { return teachingLanguage; }
        public void setTeachingLanguage(String teachingLanguage) { this.teachingLanguage = teachingLanguage; }
        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }
        public String getMainTextbook() { return mainTextbook; }
        public void setMainTextbook(String mainTextbook) { this.mainTextbook = mainTextbook; }
        public String getReferenceMaterials() { return referenceMaterials; }
        public void setReferenceMaterials(String referenceMaterials) { this.referenceMaterials = referenceMaterials; }
        public String getStudentTasks() { return studentTasks; }
        public void setStudentTasks(String studentTasks) { this.studentTasks = studentTasks; }
        public String getObjectives() { return objectives; }
        public void setObjectives(String objectives) { this.objectives = objectives; }
        public String getFacilities() { return facilities; }
        public void setFacilities(String facilities) { this.facilities = facilities; }
    }

    public static class SyllabusResponse {
        private Long id;
        private Long subjectId;
        private String subjectCode;
        private String subjectName;
        private Integer credits;
        private Integer theoryPeriods;
        private Integer practicePeriods;
        private Integer selfStudyPeriods;
        private String courseType;
        private String prerequisite;
        private String teachingLanguage;
        private String summary;
        private String mainTextbook;
        private String referenceMaterials;
        private String studentTasks;
        private String objectives;
        private String facilities;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getSubjectId() { return subjectId; }
        public void setSubjectId(Long subjectId) { this.subjectId = subjectId; }
        public String getSubjectCode() { return subjectCode; }
        public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
        public String getSubjectName() { return subjectName; }
        public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
        public Integer getCredits() { return credits; }
        public void setCredits(Integer credits) { this.credits = credits; }
        public Integer getTheoryPeriods() { return theoryPeriods; }
        public void setTheoryPeriods(Integer theoryPeriods) { this.theoryPeriods = theoryPeriods; }
        public Integer getPracticePeriods() { return practicePeriods; }
        public void setPracticePeriods(Integer practicePeriods) { this.practicePeriods = practicePeriods; }
        public Integer getSelfStudyPeriods() { return selfStudyPeriods; }
        public void setSelfStudyPeriods(Integer selfStudyPeriods) { this.selfStudyPeriods = selfStudyPeriods; }
        public String getCourseType() { return courseType; }
        public void setCourseType(String courseType) { this.courseType = courseType; }
        public String getPrerequisite() { return prerequisite; }
        public void setPrerequisite(String prerequisite) { this.prerequisite = prerequisite; }
        public String getTeachingLanguage() { return teachingLanguage; }
        public void setTeachingLanguage(String teachingLanguage) { this.teachingLanguage = teachingLanguage; }
        public String getSummary() { return summary; }
        public void setSummary(String summary) { this.summary = summary; }
        public String getMainTextbook() { return mainTextbook; }
        public void setMainTextbook(String mainTextbook) { this.mainTextbook = mainTextbook; }
        public String getReferenceMaterials() { return referenceMaterials; }
        public void setReferenceMaterials(String referenceMaterials) { this.referenceMaterials = referenceMaterials; }
        public String getStudentTasks() { return studentTasks; }
        public void setStudentTasks(String studentTasks) { this.studentTasks = studentTasks; }
        public String getObjectives() { return objectives; }
        public void setObjectives(String objectives) { this.objectives = objectives; }
        public String getFacilities() { return facilities; }
        public void setFacilities(String facilities) { this.facilities = facilities; }
    }
}
