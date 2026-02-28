package com.example.project.service;

import com.example.project.entity.AllowedEmail;
import com.example.project.entity.User;
import com.example.project.repository.AllowedEmailRepository;
import com.example.project.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final String ADMIN_EMAIL = "dohoanganh28072004@gmail.com";

    private final UserRepository userRepository;
    private final AllowedEmailRepository allowedEmailRepository;

    public CustomOAuth2UserService(UserRepository userRepository, AllowedEmailRepository allowedEmailRepository) {
        this.userRepository = userRepository;
        this.allowedEmailRepository = allowedEmailRepository;
    }

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(request);
        String email = oauth2User.getAttribute("email");
        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException(new org.springframework.security.oauth2.core.OAuth2Error("invalid_request", "Email not provided by Google", null));
        }

        User.Role role;
        if (ADMIN_EMAIL.equalsIgnoreCase(email)) {
            role = User.Role.ADMIN;
        } else {
            AllowedEmail allowed = allowedEmailRepository.findByEmail(email).orElse(null);
            if (allowed == null) {
                throw new OAuth2AuthenticationException(new org.springframework.security.oauth2.core.OAuth2Error("access_denied", "Email chưa được duyệt đăng nhập. Vui lòng liên hệ phòng đào tạo.", null));
            }
            role = allowed.getRole();
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = new User();
            user.setEmail(email);
            user.setFullname(oauth2User.getAttribute("name"));
            user.setPictureUrl(oauth2User.getAttribute("picture"));
            user.setPassword(null);
            user.setStatus(User.Status.ACTIVE);
            user.setRole(role);
            userRepository.save(user);
        } else {
            user.setFullname(oauth2User.getAttribute("name"));
            user.setPictureUrl(oauth2User.getAttribute("picture"));
            user.setRole(role);
            user.setStatus(User.Status.ACTIVE);
            userRepository.save(user);
        }

        return new CustomOAuth2User(
            oauth2User.getAttributes(),
            "sub",
            user.getId(),
            user.getEmail(),
            user.getFullname(),
            user.getPictureUrl(),
            role,
            Stream.concat(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name())).stream(),
                oauth2User.getAuthorities().stream()
            ).collect(Collectors.toSet())
        );
    }
}
