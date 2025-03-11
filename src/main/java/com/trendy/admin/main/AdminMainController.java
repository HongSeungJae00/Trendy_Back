package com.trendy.admin.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/main")
@RequiredArgsConstructor
public class AdminMainController {

    private final AdminMainService adminMainService;
    
    @GetMapping
    public String mainPage(ModelMap modelMap) {  // Model을 ModelMap으로 변경
        modelMap.addAttribute("orders", adminMainService.getAllOrders());
        modelMap.addAttribute("stocks", adminMainService.getAllStocks());
        return "AdMainPage";
    }
}