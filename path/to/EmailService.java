package com.trendy.login.signup;

import com.trendy.login.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    // 인증 이메일 발송 메서드
    public void sendVerificationEmail(User user, String verificationLink) {
        String subject = "이메일 인증 안내";
        String content = "안녕하세요, " + user.getUsername() + "님.\n\n"
                + "아래 링크를 클릭하여 이메일 인증을 완료해주세요:\n"
                + verificationLink + "\n\n"
                + "감사합니다.";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(content, false); // HTML 포맷일 경우 true
            mailSender.send(message);
            logger.info("인증 이메일이 성공적으로 발송되었습니다: {}", user.getEmail());
        } catch (MessagingException e) {
            logger.error("이메일 발송 실패: {}", e.getMessage());
            throw new RuntimeException("이메일 발송 실패: " + e.getMessage());
        }
    }

    // 테스트 이메일 발송 메서드
    public void sendTestEmail(String toEmail) {
        String subject = "테스트 이메일";
        String content = "이것은 테스트 이메일입니다.";

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content, true); // HTML 포맷일 경우 true
            mailSender.send(message);
            logger.info("테스트 이메일이 성공적으로 발송되었습니다: {}", toEmail);
        } catch (MessagingException e) {
            logger.error("테스트 이메일 전송 실패: {}", e.getMessage());
            throw new RuntimeException("테스트 이메일 전송 실패: " + e.getMessage());
        }
    }
} 