package com.trendy.mypage.resell;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.trendy.product.ProductOption;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sales_data")
public class SalesData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @Column(name = "model_id", nullable = false)
    private String modelId;

    @ManyToOne
    @JoinColumn(name = "option_id", nullable = false)
    private ProductOption productOption;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;


    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    
}
