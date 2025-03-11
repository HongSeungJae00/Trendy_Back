package com.trendy.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api") // 고유한 경로 설정
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    @ResponseBody
    @PostMapping("/mail")
    public Map<String, Object> mailSend(@RequestParam("mail") String mail) {
        Map<String, Object> response = new HashMap<>();
        try {
            int number = emailService.sendMail(mail);
            verificationCodes.put(mail, String.valueOf(number));
            response.put("success", true);
            response.put("message", "인증번호가 발송되었습니다.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    @ResponseBody
    @GetMapping("/mailCheck")
    public boolean mailCheck(
        @RequestParam("userNumber") String userNumber,
        @RequestParam("mail") String mail
    ) {
        String storedNumber = verificationCodes.get(mail);
        if (storedNumber == null) {
            return false;
        }
        boolean isValid = storedNumber.equals(userNumber);
        if (isValid) {
            verificationCodes.remove(mail);
        }
        return isValid;
    }
}
