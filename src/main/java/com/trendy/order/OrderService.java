package com.trendy.order;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import com.trendy.order.Order.OrderStatus;
import com.trendy.product.ProductOption;
import com.trendy.product.ProductRepository;
import com.trendy.product.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Map<String, Object> createOrder(OrderDTO orderDTO) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1. 상품 조회
            Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

            // 2. 재고 확인
            ProductOption option = product.getProductOptions().stream()
                .filter(o -> o.getId().equals(orderDTO.getOptionId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("상품 옵션을 찾을 수 없습니다."));
            
            if (option.getStockQuantity() < orderDTO.getQuantity()) {
                throw new IllegalStateException("재고가 부족합니다.");
            }

            // 3. 주문 생성
            Order order = Order.builder()
                .userId(orderDTO.getUserId())
                .productId(orderDTO.getProductId())
                .optionId(orderDTO.getOptionId())
                .modelId(product.getModelId())
                .quantity(orderDTO.getQuantity())
                .price(orderDTO.getPrice())
                .shippingAddress(orderDTO.getShippingAddress())
                .pickupLocation(orderDTO.getPickupLocation())
                .status(OrderStatus.in_progress)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

            // 4. 재고 감소
            option.setStockQuantity(option.getStockQuantity() - orderDTO.getQuantity());

            // 5. 주문 저장
            Order savedOrder = orderRepository.save(order);
            
            result.put("success", true);
            result.put("message", "주문이 성공적으로 생성되었습니다.");
            result.put("orderId", savedOrder.getOrderId());
            
        } catch (Exception e) {
            log.error("주문 생성 중 오류 발생: {}", e.getMessage());
            result.put("success", false);
            result.put("message", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        
        return result;
    }
}