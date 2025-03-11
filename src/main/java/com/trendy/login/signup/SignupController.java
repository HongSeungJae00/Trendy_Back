package com.trendy.login.signup;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.trendy.login.User;
import com.trendy.login.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<?> signup(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        try {
            // 아이디 검증
            if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "아이디는 필수 입력값입니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // 아이디 중복 확인
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                response.put("status", "error");
                response.put("message", "이미 사용 중인 아이디입니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // 이메일 검증
            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "이메일은 필수 입력값입니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // 비밀번호 검증
            if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
                response.put("status", "error");
                response.put("message", "비밀번호는 필수 입력값입니다.");
                return ResponseEntity.badRequest().body(response);
            }

            if (user.getPassword().length() < 8) {
                response.put("status", "error");
                response.put("message", "비밀번호는 8자 이상이어야 합니다.");
                return ResponseEntity.badRequest().body(response);
            }

            // 비밀번호 암호화
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // 사용자 저장
            userRepository.save(user);

            response.put("status", "success");
            response.put("message", "회원가입이 완료되었습니다.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "회원가입 중 오류가 발생했습니다. 관리자에게 문의해주세요.");
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam("username") String username) {
        Map<String, String> response = new HashMap<>();

        if (username == null || username.trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "아이디를 입력해주세요.");
            return ResponseEntity.badRequest().body(response);
        }

        boolean isDuplicate = userRepository.findByUsername(username).isPresent();
        if (isDuplicate) {
            response.put("status", "error");
            response.put("message", "이미 사용 중인 아이디입니다.");
        } else {
            response.put("status", "success");
            response.put("message", "사용 가능한 아이디입니다.");
        }

        return ResponseEntity.ok(response);
    }
}
