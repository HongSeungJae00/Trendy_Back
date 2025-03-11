package com.trendy.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trendy.security.JwtTokenProvider;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 생성
        String token = jwtTokenProvider.createToken(username);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<?> oauth2Success(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof DefaultOAuth2User) {
            DefaultOAuth2User oauth2User = (DefaultOAuth2User) authentication.getPrincipal();
            
            // OAuth2 인증 정보를 기반으로 사용자 식별 (Google, Kakao 등)
            String username = oauth2User.getAttribute("email"); // 이메일을 username으로 사용 (필요시 수정)

            // JWT 발급 (OAuth2에도 동일한 JWT 방식 적용)
            String jwtToken = jwtTokenProvider.createToken(username);
            
            return ResponseEntity.ok(Map.of("token", jwtToken));
        }
        return ResponseEntity.status(401).body("소셜 로그인 인증 실패");
    }
}
