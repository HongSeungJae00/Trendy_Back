//package com.trendy.admin.order;
//
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "Orders")
//@Getter
//@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class AdminOrder {
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "order_id")
//    private Long orderId;
//
//    @Column(name = "user_id")
//    private Long userId;
//
//    @Column(name = "option_id")
//    private Long optionId;
//
//    @Column(name = "model_id")
//    private String modelId;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "created_by")
//    private CreatedBy createdBy;
//
//    private Integer quantity;
//    private Integer price;
//
//    @Enumerated(EnumType.STRING)
//    private OrderStatus status;
//
//    @Column(name = "shipping_address")
//    private String shippingAddress;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "pickup_location")
//    private PickupLocation pickupLocation;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    // Enum 클래스들
//    public enum CreatedBy {
//        admin, seller
//    }
//
//    public enum OrderStatus {
//        in_progress, shipping, completed
//    }
//
//    public enum PickupLocation {
//        Jamsil, Mapo, Gangnam
//    }
//
//    @Builder
//    public AdminOrder(Long userId, Long optionId, String modelId, CreatedBy createdBy,
//                     Integer quantity, Integer price, OrderStatus status,
//                     String shippingAddress, PickupLocation pickupLocation) {
//        this.userId = userId;
//        this.optionId = optionId;
//        this.modelId = modelId;
//        this.createdBy = createdBy;
//        this.quantity = quantity;
//        this.price = price;
//        this.status = status;
//        this.shippingAddress = shippingAddress;
//        this.pickupLocation = pickupLocation;
//    }
//}