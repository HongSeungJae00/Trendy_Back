package com.trendy.health;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthCheckController {

    @GetMapping("/health")
    public String healthCheck() {
        return "health";
    }
}
