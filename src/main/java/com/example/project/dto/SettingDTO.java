package com.example.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SettingDTO {

    public static class SettingRequest {
        @NotBlank(message = "Name is mandatory")
        @Size(max = 20)
        private String name;

        @NotBlank(message = "Type is mandatory")
        private String type;

        @NotBlank(message = "Value is mandatory")
        @Size(max = 100)
        private String value;

        @NotNull(message = "Priority is mandatory")
        private Integer priority;

        @Size(max = 200)
        private String description;

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public Integer getPriority() { return priority; }
        public void setPriority(Integer priority) { this.priority = priority; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class SettingResponse {
        private Long id;
        private String name;
        private String type;
        private String value;
        private Integer priority;
        private String description;

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        public Integer getPriority() { return priority; }
        public void setPriority(Integer priority) { this.priority = priority; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
}