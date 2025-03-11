package com.trendy.login;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage") // 기본 경로 유지
public class UserController {

	@GetMapping
    public String myPage(Authentication authentication, Model model) {
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) principal;
                // OAuth2 사용자 정보 추가
                model.addAttribute("username", oauth2User.getAttribute("name"));
                model.addAttribute("email", oauth2User.getAttribute("email"));
                model.addAttribute("profileImageUrl", oauth2User.getAttribute("picture"));
            } else if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                // 일반 사용자 정보 추가
                model.addAttribute("username", userDetails.getUsername());
                model.addAttribute("email", userDetails.getEmail());
                model.addAttribute("profileImageUrl", userDetails.getProfileImageUrl());
            } else {
                // 로그인되지 않은 사용자 기본값
                model.addAttribute("username", "이름 없음");
                model.addAttribute("email", "이메일 없음");
                model.addAttribute("profileImageUrl", "/default-profile.png");
            }
        }

        return "mypage"; // mypage.html 템플릿 렌더링
    }
}