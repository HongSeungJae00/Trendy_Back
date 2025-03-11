package com.trendy.admin.main;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "Warehouse_Stocks")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminMainStock {
    public enum WarrantyGrade {
        A, B, C, Fail
    }
    
    public enum Brand {
        Nike, Adidas, Asics, New_Balance, Reebok, Others
    }
    
    public enum Size {
        SIZE_230("230"), 
        SIZE_240("240"), 
        SIZE_250("250"), 
        SIZE_260("260"), 
        SIZE_270("270"), 
        SIZE_280("280"), 
        SIZE_290("290");
        
        private final String value;
        
        Size(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static Size fromValue(String value) {
            for (Size size : values()) {
                if (size.value.equals(value)) {
                    return size;
                }
            }
            throw new IllegalArgumentException("Unknown size value: " + value);
        }
    }
    
    public enum Gender {
        male, female
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stockId;  // @Column 어노테이션 제거
    
    private Long warehouseId;
    
    private Long productId;
    
    private String modelId;
    
    private Integer stockQuantity;
    
    @Enumerated(EnumType.STRING)
    private WarrantyGrade warrantyGrade;
    
    @Enumerated(EnumType.STRING)
    private Brand brand;
    
    private String color;
    
    @Column(name = "size")
    private String size;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
}