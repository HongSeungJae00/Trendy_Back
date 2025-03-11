package com.trendy.login.signup;

import com.trendy.login.User;
import com.trendy.login.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

    private final UserService userService;
    private final EmailVerificationService emailVerificationService;
    private final EmailService emailService;

    @PostMapping("/send-verification-email")
    public ResponseEntity<?> sendVerificationEmail(@RequestParam String email) {
        User user = userService.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("사용자를 찾을 수 없습니다.", "error"));
        }
        if (user.isEmailVerified()) {
            return ResponseEntity.badRequest().body(new ErrorResponse("이미 인증된 이메일입니다.", "error"));
        }

        String code = emailVerificationService.generateVerificationCode(user);
        String verificationLink = "http://localhost:8080/signup/verify-email?code=" + code + "&email=" + email;
        emailService.sendVerificationEmail(user, verificationLink); // 인증 링크 전송

        return ResponseEntity.ok(new SuccessResponse("인증 번호가 성공적으로 발송되었습니다.", "success"));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam String code, @RequestParam String email, Model model) {
        User user = userService.findByEmail(email).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        boolean isVerified = emailVerificationService.verifyCode(code, user);
        if (isVerified) {
            return ResponseEntity.ok(new SuccessResponse("이메일 인증이 성공적으로 완료되었습니다.", "success"));
        } else {
            return ResponseEntity.badRequest().body(new ErrorResponse("인증 번호가 유효하지 않거나 만료되었습니다.", "error"));
        }
    }
} 