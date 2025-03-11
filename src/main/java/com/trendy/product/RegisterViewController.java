package com.trendy.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterViewController {

    @GetMapping("/product-registration")
    public String showProductRegistrationPage() {
        return "product-registration"; // templates/product-registration.html 파일
    }
    
    
}
