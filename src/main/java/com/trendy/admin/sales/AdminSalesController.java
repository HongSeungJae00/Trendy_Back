package com.trendy.admin.sales;

import java.util.List;
import org.springframework.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/sales")
@RequiredArgsConstructor
public class AdminSalesController {
    
    private final AdminSalesService salesService;

    @GetMapping
    public String listSales(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "sort", defaultValue = "desc") String sort,
            ModelMap modelMap) {
        
        List<AdminSalesDTO> salesList;
        if (StringUtils.hasText(keyword)) {
            salesList = salesService.searchSales(keyword, sort);
        } else {
            salesList = salesService.getAllSales(sort);
        }
        
        modelMap.addAttribute("salesList", salesList);
        modelMap.addAttribute("keyword", keyword);
        modelMap.addAttribute("sort", sort);
        
        return "AdSalesPage";
    }

    @PostMapping("/delete")
    public String deleteSales(
            @RequestParam(name = "selectedSales", required = false) List<Long> saleIds) {
        if (saleIds != null && !saleIds.isEmpty()) {
            salesService.deleteSales(saleIds);
        }
        return "redirect:/admin/sales";
    }
}