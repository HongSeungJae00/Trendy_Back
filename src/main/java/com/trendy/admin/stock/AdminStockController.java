package com.trendy.admin.stock;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/stock")
@RequiredArgsConstructor
public class AdminStockController {
    
    private final AdminStockService stockService;

    @GetMapping
    public String listStocks(Model model) {
        model.addAttribute("stocks", stockService.getAllStocks());
        return "AdStockPage";  // 기본 목록 페이지
    }

    // 등록 폼
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("stock", new AdminStockDTO());
        model.addAttribute("warehouses", stockService.getAllWarehouses());
        return "AdStockRegister";
    }

    // 등록 처리
    @PostMapping("/register")
    public String registerStock(@ModelAttribute AdminStockDTO stockDTO) {
        stockService.saveStock(stockDTO);
        return "redirect:/admin/stock";
    }

    @GetMapping("/{stockId}/modify")
    public String showModifyForm(@PathVariable("stockId") Long stockId, Model model) {
        model.addAttribute("stock", stockService.getStockById(stockId));
        model.addAttribute("warehouses", stockService.getAllWarehouses());
        return "AdStockModify";
    }

    // 수정 처리
    @PostMapping("/{stockId}/modify")
    public String updateStock(@PathVariable("stockId") Long stockId, @ModelAttribute AdminStockDTO stockDTO) {
        stockDTO.setStockId(stockId);
        stockService.updateStock(stockDTO);
        return "redirect:/admin/stock";
    }

    // 삭제 처리
    @PostMapping("/delete")
    public String deleteStocks(@RequestParam("selectedStocks") List<Long> stockIds) {
        stockService.deleteStocks(stockIds);
        return "redirect:/admin/stock";
    }
}