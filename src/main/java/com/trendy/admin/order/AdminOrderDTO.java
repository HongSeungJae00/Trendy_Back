// package com.trendy.admin.order;

// import java.time.LocalDateTime;

// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// public class AdminOrderDTO {
//    private Long orderId;
//    private Long userId;
//    private Long optionId;
//    private String modelId;
//    private String createdBy;
//    private Integer quantity;
//    private Integer price;
//    private String status;
//    private String shippingAddress;
//    private String pickupLocation;
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;

//    // Entity -> DTO 변환 메서드
//    public static AdminOrderDTO from(AdminOrder order) {
//        return AdminOrderDTO.builder()
//                .orderId(order.getOrderId())
//                .userId(order.getUserId())
//                .optionId(order.getOptionId())
//                .modelId(order.getModelId())
//                .createdBy(order.getCreatedBy().name())
//                .quantity(order.getQuantity())
//                .price(order.getPrice())
//                .status(order.getStatus().name())
//                .shippingAddress(order.getShippingAddress())
//                .pickupLocation(order.getPickupLocation().name())
//                .createdAt(order.getCreatedAt())
//                .updatedAt(order.getUpdatedAt())
//                .build();
//    }

//    // DTO -> Entity 변환 메서드
//    public AdminOrder toEntity() {
//        return AdminOrder.builder()
//                .userId(this.userId)
//                .optionId(this.optionId)
//                .modelId(this.modelId)
//                .createdBy(AdminOrder.CreatedBy.valueOf(this.createdBy))
//                .quantity(this.quantity)
//                .price(this.price)
//                .status(AdminOrder.OrderStatus.valueOf(this.status))
//                .shippingAddress(this.shippingAddress)
//                .pickupLocation(AdminOrder.PickupLocation.valueOf(this.pickupLocation))
//                .build();
//    }
// }