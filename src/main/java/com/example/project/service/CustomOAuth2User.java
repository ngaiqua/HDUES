package com.example.project.service;

import com.example.project.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2User delegate;
    private final Long userId;
    private final String email;
    private final String fullName;
    private final String pictureUrl;
    private final User.Role role;

    public CustomOAuth2User(Map<String, Object> attributes, String nameAttributeKey,
                            Long userId, String email, String fullName, String pictureUrl,
                            User.Role role, Collection<? extends GrantedAuthority> authorities) {
        this.delegate = new org.springframework.security.oauth2.core.user.DefaultOAuth2User(
            authorities, attributes, nameAttributeKey != null ? nameAttributeKey : "sub");
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.pictureUrl = pictureUrl;
        this.role = role;
    }

    @Override
    public Map<String, Object> getAttributes() { return delegate.getAttributes(); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return delegate.getAuthorities(); }

    @Override
    public String getName() { return delegate.getName(); }

    public Long getUserId() { return userId; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPictureUrl() { return pictureUrl; }
    public User.Role getRole() { return role; }
}
