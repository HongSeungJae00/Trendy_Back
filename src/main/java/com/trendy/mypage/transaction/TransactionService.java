package com.trendy.mypage.transaction;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trendy.login.User;
import com.trendy.login.UserRepository;
import com.trendy.mypage.resell.SalesData;
import com.trendy.mypage.resell.SalesDataDTO;
import com.trendy.mypage.resell.SalesDataRepository;
import com.trendy.order.Order;
import com.trendy.order.OrderDTO;
import com.trendy.order.OrderRepository;
import com.trendy.product.Product;
import com.trendy.product.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {
    
    private final OrderRepository orderRepository;
    private final SalesDataRepository salesDataRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<OrderDTO> getOrderList() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Order> orders = orderRepository.findByUserId(user.getUserId());
            if (orders.isEmpty()) {
                return List.of();  // 빈 리스트 반환
            }
            
            return orders.stream()
                    .map(order -> new OrderDTO(
                            order.getModelId(),
                            order.getPrice(),
                            order.getCreatedAt().toString()
                    ))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get order list: " + e.getMessage());
        }
    }

    public List<SalesDataDTO> getSalesList() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<SalesData> salesList = salesDataRepository.findByUserId(user.getUserId());

        return salesList.stream()
                .map(sale -> new SalesDataDTO(
                        sale.getModelId(),   // Model ID
                        sale.getPrice(),     // Price
                        sale.getCreatedAt().toString()  // Created At
                ))
                .collect(Collectors.toList());
    }

    public List<String> getProductImages() {
        return productRepository.findAll().stream()
                .map(Product::getImageUrl)
                .collect(Collectors.toList());
    }

    public String getProductImageUrl(Long productId) {
        try {
            return productRepository.findById(productId)
                    .map(Product::getImageUrl)
                    .orElse("");  // 이미지가 없을 경우 빈 문자열 반환
        } catch (Exception e) {
            throw new RuntimeException("Failed to get product image: " + e.getMessage());
        }
    }
}
