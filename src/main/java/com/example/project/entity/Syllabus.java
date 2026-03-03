package com.example.project.entity;

import jakarta.persistence.*;

@Entity
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer credits;           // Số tín chỉ
    private Integer theoryPeriods;     // Lí thuyết (tiết)
    private Integer practicePeriods;   // Thực hành (tiết)
    private Integer selfStudyPeriods;  // Tự học (tiết)
    private String courseType;         // Loại học phần (Bắt buộc, Tự chọn...)
    private String prerequisite;       // Học phần tiên quyết
    private String teachingLanguage;   // Ngôn ngữ giảng dạy

    @Column(length = 2000)
    private String summary;            // Tóm tắt
    @Column(length = 2000)
    private String mainTextbook;       // Giáo trình chính
    @Column(length = 2000)
    private String referenceMaterials; // Tài liệu tham khảo
    @Column(length = 2000)
    private String studentTasks;       // Nhiệm vụ của sinh viên
    @Column(length = 2000)
    private String objectives;         // Mục tiêu
    @Column(length = 2000)
    private String facilities;         // Cơ sở vật chất

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    // Getter & Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public User getCreatedBy() { return createdBy; }
    public void setCreatedBy(User createdBy) { this.createdBy = createdBy; }

    public User getUpdatedBy() { return updatedBy; }
    public void setUpdatedBy(User updatedBy) { this.updatedBy = updatedBy; }
}
