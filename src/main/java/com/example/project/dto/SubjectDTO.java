package com.example.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SubjectDTO {

    public static class SubjectRequest {
        @NotBlank(message = "Subject code is mandatory")
        @Size(max = 50)
        private String code;

        @NotBlank(message = "Subject name is mandatory")
        @Size(max = 255)
        private String name;

        private String description;

        // Getters and Setters
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class SubjectResponse {
        private Long id;
        private String code;
        private String name;
        private String description;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}