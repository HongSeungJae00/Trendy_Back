package com.trendy.mypage.resell;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesDataDTO {
    private Long id;
    private String modelId;
    private Long optionId;      // ProductOption의 ID
    private Long userId;        // User의 ID
    private Integer quantity;
    private Integer price;
    private LocalDate saleDate;
    private LocalDateTime createdAt;

    public SalesDataDTO(String modelId, int price, String createdAt) {
        this.modelId = modelId;
        this.price = price;
        this.createdAt = LocalDateTime.parse(createdAt);
    }

    // Entity -> DTO 변환 메서드
    public static SalesDataDTO fromEntity(SalesData salesData) {
        return SalesDataDTO.builder()
                .id(salesData.getId())
                .modelId(salesData.getModelId())
                .optionId(salesData.getProductOption().getId())
                .userId(salesData.getUserId())
                .quantity(salesData.getQuantity())
                .price(salesData.getPrice())
                .saleDate(salesData.getSaleDate())
                .createdAt(salesData.getCreatedAt())
                .build();
    }
}