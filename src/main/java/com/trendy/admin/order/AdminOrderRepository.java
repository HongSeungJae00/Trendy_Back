package com.trendy.admin.order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.trendy.order.Order;

@Repository
public interface AdminOrderRepository extends JpaRepository<Order, Long> {

   // 주문 목록을 생성일(createdAt) 기준 내림차순으로 조회
   List<Order> findAllByOrderByOrderIdDesc();

   // 주문 ID로 주문 조회
   Optional<Order> findByOrderId(Long orderId);

   // 특정 상태(Status)의 주문 목록 조회
   List<Order> findByStatus(Order status);

   // 주문 삭제 (기본 제공)
   void deleteByOrderId(Long orderId);

   // 다중 주문 삭제
   void deleteAllByOrderIdIn(List<Long> orderIds);

   // 가장 큰 주문 ID 조회
   @Query("SELECT COALESCE(MAX(o.orderId), 0) FROM Order o")
   Long findMaxOrderId();
}
