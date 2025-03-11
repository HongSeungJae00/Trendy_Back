package com.trendy.admin.sales;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Sales_Data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminSales {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long saleId;
    
    @Column(name = "option_id")
    private Long optionId;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "model_id")
    private String modelId;
    
    @Column(name = "total_price")
    private Integer totalPrice;
    
    @Column(name = "sale_date")
    private LocalDate saleDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}