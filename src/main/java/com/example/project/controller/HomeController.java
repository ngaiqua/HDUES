package com.example.project.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.project.service.CustomOAuth2User;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomOAuth2User user) {
        if (user != null) {
            return "redirect:/dashboard/" + user.getRole().name().toLowerCase().replace("_department", "");
        }
        // Khi bật lại bảo mật: đổi thành return "redirect:/login";
        return "redirect:/dashboard/student";
    }
}
