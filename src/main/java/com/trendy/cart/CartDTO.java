package com.trendy.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Data;

@Data
@Builder
public class CartDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private Long optionId;
    private String name;
    private String brand;
    private int price;
    private String image;
    private String size;
    private int quantity;
    private int stockQuantity;
} 