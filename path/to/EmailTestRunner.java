package com.trendy;

import com.trendy.login.signup.EmailService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmailTestRunner implements CommandLineRunner {

    private final EmailService emailService;

    public EmailTestRunner(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 테스트 이메일 발송 (실제 이메일 주소로 변경)
        emailService.sendTestEmail("sophist1420@gmail.com"); // 실제 수신 가능한 이메일 주소로 변경
    }
} 