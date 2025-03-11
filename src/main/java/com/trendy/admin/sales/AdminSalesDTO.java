package com.trendy.admin.sales;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSalesDTO {
    private Long saleId;
    private Long optionId;
    private Long userId;
    private Integer quantity;
    private String modelId;
    private Integer totalPrice;
    private LocalDate saleDate;
    private LocalDateTime createdAt;
    
    // 추가 필드
    private String category;  // 카테고리 정보
    private Integer price;    // 개별 상품 가격
}