package com.trendy.admin.stock;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "warehouse_stocks")
public class AdminStock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;
    
    private Long warehouseId;
    private Long productId;
    private String modelId;
    private Integer stockQuantity;
    
    @Column(columnDefinition = "ENUM('A', 'B', 'C', 'Fail')")
    private String warrantyGrade;
    
    @Column(columnDefinition = "ENUM('Nike', 'Adidas', 'Asics', 'New_Balance', 'Reebok', 'Others')")
    private String brand;
    
    private String color;
    
    @Column(name = "size")
    private String size;
    
    @Column(columnDefinition = "ENUM('male', 'female')")
    private String gender;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
}