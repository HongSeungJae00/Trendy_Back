package com.trendy.order;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "model_id")
    private String modelId;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "price")
    private Integer price;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    
    @Column(name = "shipping_address")
    private String shippingAddress;
    
    @Enumerated(EnumType.STRING)
    private PickupLocation pickupLocation;
    
    @Column(name = "option_id")
    private Long optionId;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public enum OrderStatus {
        in_progress,    // 진행중
        shipping,       // 배송중
        completed       // 완료됨
    }
    
    public enum PickupLocation {
        Jamsil,        // 잠실점
        Mapo,          // 마포점
        Gangnam        // 강남점
    }
    
    
}