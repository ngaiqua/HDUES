package com.example.project.service;

import com.example.project.dto.UserDTO;
import com.example.project.entity.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // A simple mapper method (in a real app, consider using MapStruct)
    private UserDTO.UserResponse convertToDto(User user) {
        UserDTO.UserResponse dto = new UserDTO.UserResponse();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullname(user.getFullname());
        dto.setRole(user.getRole().name());
        dto.setStatus(user.getStatus().name());
        return dto;
    }

    public List<UserDTO.UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Other CRUD methods would go here...
}