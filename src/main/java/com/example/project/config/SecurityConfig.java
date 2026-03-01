package com.example.project.config;

import com.example.project.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
                // --- BẬT LẠI KHI ĐÃ DEVELOP XONG: chặn truy cập chưa đăng nhập, phân quyền theo role ---
                // .requestMatchers("/", "/login", "/login/**", "/css/**", "/js/**", "/images/**", "/error").permitAll()
                // .requestMatchers("/dashboard/admin/**", "/admin/**").hasRole("ADMIN")
                // .requestMatchers("/dashboard/training/**", "/training/**").hasAnyRole("ADMIN", "TRAINING_DEPARTMENT")
                // .requestMatchers("/dashboard/lecturer/**", "/dashboard/teacher/**").hasRole("LECTURER")
                // .requestMatchers("/dashboard/student/**").hasRole("STUDENT")
                // .requestMatchers("/dashboard/**").authenticated()
                // .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .userInfoEndpoint(u -> u.userService(customOAuth2UserService))
                .successHandler(successHandler())
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"));
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            var role = authentication.getAuthorities().stream()
                .filter(a -> a.getAuthority().startsWith("ROLE_"))
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .findFirst()
                .orElse("STUDENT");
            String path = switch (role) {
                case "ADMIN" -> "/dashboard/admin";
                case "TRAINING_DEPARTMENT" -> "/dashboard/training";
                case "LECTURER" -> "/dashboard/lecturer";
                default -> "/dashboard/student";
            };
            response.sendRedirect(request.getContextPath() + path);
        };
    }
}
