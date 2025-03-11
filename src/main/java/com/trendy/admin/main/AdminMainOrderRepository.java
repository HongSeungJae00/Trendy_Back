package com.trendy.admin.main;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trendy.order.Order;

@Repository
public interface AdminMainOrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByOrderByOrderIdDesc();  // id -> orderId로 변경
}