package com.trendy.mypage.transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trendy.mypage.resell.SalesDataDTO;
import com.trendy.order.OrderDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // 주문 목록 조회 (DTO 반환)
    @GetMapping("/orderlist")
    public ResponseEntity<Map<String, Object>> getOrderList() {
        try {
            List<OrderDTO> orders = transactionService.getOrderList();
            
            // 각 주문의 상품 ID로 이미지 URL 조회
            List<String> productImages = orders.stream()
                .map(order -> transactionService.getProductImageUrl(order.getProductId()))
                .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("orders", orders);
            response.put("productImages", productImages);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 판매 내역 조회 (DTO 반환)
    @GetMapping("/saleslist")
    public ResponseEntity<Map<String, Object>> getSalesList() {
        try {
            List<SalesDataDTO> sales = transactionService.getSalesList();
            
            // 각 판매의 상품 ID로 이미지 URL 조회
            List<String> productImages = sales.stream()
                .map(sale -> transactionService.getProductImageUrl(Long.parseLong(sale.getModelId())))
                .collect(Collectors.toList());
            
            Map<String, Object> response = new HashMap<>();
            response.put("sales", sales);
            response.put("productImages", productImages);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
