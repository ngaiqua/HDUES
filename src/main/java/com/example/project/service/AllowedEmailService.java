package com.example.project.service;

import com.example.project.entity.AllowedEmail;
import com.example.project.entity.User;
import com.example.project.repository.AllowedEmailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Service
public class AllowedEmailService {

    private final AllowedEmailRepository allowedEmailRepository;
    private static final String ADMIN_EMAIL = "dohoanganh28072004@gmail.com";

    public AllowedEmailService(AllowedEmailRepository allowedEmailRepository) {
        this.allowedEmailRepository = allowedEmailRepository;
    }

    public List<AllowedEmail> findAll() {
        return allowedEmailRepository.findAll();
    }

    public Optional<AllowedEmail> findById(Long id) {
        return allowedEmailRepository.findById(id);
    }

    public Optional<AllowedEmail> findByEmail(String email) {
        return allowedEmailRepository.findByEmail(email);
    }

    @Transactional
    public AllowedEmail addEmail(String email, User.Role role, String approvedBy) {
        if (allowedEmailRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists in the allowed list.");
        }
        AllowedEmail a = new AllowedEmail();
        a.setEmail(email.trim().toLowerCase());
        a.setRole(role != null ? role : User.Role.STUDENT);
        a.setApprovedBy(approvedBy);
        a.setApprovedAt(LocalDateTime.now());
        return allowedEmailRepository.save(a);
    }

    @Transactional
    public void deleteById(Long id) {
        if (id == null) {
            return;
        }
        AllowedEmail existing = allowedEmailRepository.findById(id).orElse(null);
        if (existing == null) {
            return;
        }
        // Admin có thể xóa mọi email (kể cả admin/training)
        allowedEmailRepository.deleteById(id);
    }

    @Transactional
    public void deleteByIdForTraining(Long id) {
        if (id == null) {
            return;
        }
        AllowedEmail existing = allowedEmailRepository.findById(id).orElse(null);
        if (existing == null) {
            return;
        }
        // Không cho Training Department động vào mọi mail admin / training
        if (ADMIN_EMAIL.equalsIgnoreCase(existing.getEmail())) {
            throw new IllegalArgumentException("Training department cannot delete admin email.");
        }
        if (existing.getRole() == User.Role.ADMIN || existing.getRole() == User.Role.TRAINING_DEPARTMENT) {
            throw new IllegalArgumentException("Training department can only manage STUDENT and LECTURER emails.");
        }
        allowedEmailRepository.deleteById(id);
    }

    @Transactional
    public List<String> importFromExcel(MultipartFile file, String approvedBy) throws Exception {
        return importFromExcelInternal(file, approvedBy, false);
    }

    @Transactional
    public List<String> importFromExcelForTraining(MultipartFile file, String approvedBy) throws Exception {
        return importFromExcelInternal(file, approvedBy, true);
    }

    private List<String> importFromExcelInternal(MultipartFile file, String approvedBy, boolean restrictToStudentAndLecturer) throws Exception {
        List<String> errors = new ArrayList<>();
        List<AllowedEmail> toSave = new ArrayList<>();
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                Cell emailCell = row.getCell(0);
                Cell roleCell = row.getCell(1);
                String email = getCellString(emailCell);
                if (email == null || email.isBlank()) continue;
                email = email.trim().toLowerCase();
                if (ADMIN_EMAIL.equalsIgnoreCase(email)) {
                    errors.add("Row " + (i + 1) + ": Cannot add admin email.");
                    continue;
                }
                if (allowedEmailRepository.existsByEmail(email)) {
                    errors.add("Row " + (i + 1) + ": Email already exists: " + email);
                    continue;
                }
                User.Role role = parseRole(getCellString(roleCell));
                if (restrictToStudentAndLecturer && role != User.Role.STUDENT && role != User.Role.LECTURER) {
                    errors.add("Row " + (i + 1) + ": Only STUDENT or LECTURER roles are allowed for training department.");
                    continue;
                }
                AllowedEmail a = new AllowedEmail();
                a.setEmail(email);
                a.setRole(role);
                a.setApprovedBy(approvedBy);
                a.setApprovedAt(LocalDateTime.now());
                toSave.add(a);
            }
            for (AllowedEmail a : toSave) {
                allowedEmailRepository.save(a);
            }
        }
        return errors;
    }

    private static String getCellString(Cell cell) {
        if (cell == null) return null;
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> null;
        };
    }

    private static User.Role parseRole(String s) {
        if (s == null || s.isBlank()) return User.Role.STUDENT;
        String r = s.trim().toUpperCase().replace(" ", "_");
        if (r.contains("LECTURER") || "GIANG_VIEN".equals(r)) return User.Role.LECTURER;
        if (r.contains("TRAINING") || "PHONG_DAO_TAO".equals(r)) return User.Role.TRAINING_DEPARTMENT;
        if (r.contains("ADMIN")) return User.Role.ADMIN;
        return User.Role.STUDENT;
    }
}
