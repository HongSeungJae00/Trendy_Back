package com.trendy.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import com.trendy.order.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 사용자 ID로 주문 목록 조회
    List<Order> findByUserId(Long userId);
    
    // 주문 상태로 주문 목록 조회
    List<Order> findByStatus(Order.OrderStatus status);
    
    // 사용자 ID와 주문 상태로 주문 목록 조회
    List<Order> findByUserIdAndStatus(Long userId, Order.OrderStatus status);
    
    // 픽업 위치로 주문 목록 조회
    List<Order> findByPickupLocation(Order.PickupLocation pickupLocation);
    
} 