package com.example.project.config;

import com.example.project.entity.AllowedEmail;
import com.example.project.entity.User;
import com.example.project.repository.AllowedEmailRepository;
import com.example.project.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private static final String ADMIN_EMAIL = "dohoanganh28072004@gmail.com";

    private final UserRepository userRepository;
    private final AllowedEmailRepository allowedEmailRepository;

    public DataLoader(UserRepository userRepository, AllowedEmailRepository allowedEmailRepository) {
        this.userRepository = userRepository;
        this.allowedEmailRepository = allowedEmailRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!allowedEmailRepository.existsByEmail(ADMIN_EMAIL)) {
            AllowedEmail allowed = new AllowedEmail();
            allowed.setEmail(ADMIN_EMAIL);
            allowed.setRole(User.Role.ADMIN);
            allowed.setApprovedBy("system");
            allowed.setApprovedAt(java.time.LocalDateTime.now());
            allowedEmailRepository.save(allowed);
        }
        userRepository.findByEmail(ADMIN_EMAIL).ifPresentOrElse(
            u -> {
                if (u.getRole() != User.Role.ADMIN) {
                    u.setRole(User.Role.ADMIN);
                    userRepository.save(u);
                }
            },
            () -> {
                User admin = new User();
                admin.setEmail(ADMIN_EMAIL);
                admin.setFullname("Admin");
                admin.setRole(User.Role.ADMIN);
                admin.setStatus(User.Status.ACTIVE);
                admin.setPassword(null);
                userRepository.save(admin);
            }
        );
    }
}
