package com.example.project.service;

import com.example.project.dto.SettingDTO;
import com.example.project.entity.Setting;
import com.example.project.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SettingService {

    @Autowired
    private SettingRepository settingRepository;

    private SettingDTO.SettingResponse convertToDto(Setting setting) {
        SettingDTO.SettingResponse dto = new SettingDTO.SettingResponse();
        dto.setId(setting.getId());
        dto.setName(setting.getName());
        dto.setType(setting.getType());
        dto.setValue(setting.getValue());
        dto.setPriority(setting.getPriority());
        dto.setDescription(setting.getDescription());
        return dto;
    }

    public List<SettingDTO.SettingResponse> getSettingsByType(String type) {
        return settingRepository.findByType(type).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Other CRUD methods...
}