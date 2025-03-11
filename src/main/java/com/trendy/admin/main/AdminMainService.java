package com.trendy.admin.main;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trendy.order.Order;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminMainService {
    
    private final AdminMainOrderRepository orderRepository;
    private final AdminMainStockRepository stockRepository;
    
    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByOrderIdDesc();  // 메서드명 변경
    }

    public List<AdminMainStock> getAllStocks() {
        return stockRepository.findAllByOrderByStockIdDesc();  // 메서드명 변경
    }
}