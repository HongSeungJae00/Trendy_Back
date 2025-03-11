package com.trendy.admin.stock;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdminStockDTO {
    private Long stockId;
    private Long warehouseId;
    private Long productId;
    private String modelId;
    private Integer stockQuantity;
    private String warrantyGrade;
    private String brand;
    private String color;
    private String size;
    private String gender;
}