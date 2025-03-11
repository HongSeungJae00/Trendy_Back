package com.trendy.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trendy.product.Product;
import com.trendy.product.ProductOption;
import com.trendy.product.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    @Transactional
    public Map<String, Object> addToCart(Long userId, Long optionId, int quantity) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 재고 확인
            ProductOption option = productService.getProductOption(optionId);
            if (option == null) {
                result.put("success", false);
                result.put("message", "상품 옵션을 찾을 수 없습니다.");
                return result;
            }

            // 재고 확인
            if (!productService.checkStockAvailability(optionId, quantity)) {
                result.put("success", false);
                result.put("message", "선택하신 상품의 재고가 부족합니다.");
                return result;
            }

            // 이미 장바구니에 있는지 확인
            Optional<Cart> existingCart = cartRepository.findByUserIdAndOptionId(userId, optionId);
            
            if (existingCart.isPresent()) {
                Cart cart = existingCart.get();
                int newQuantity = cart.getQuantity() + quantity;
                
                // 합산된 수량으로 재고 확인
                if (!productService.checkStockAvailability(optionId, newQuantity)) {
                    result.put("success", false);
                    result.put("message", "장바구니에 담긴 수량과 합쳐 재고가 부족합니다.");
                    return result;
                }
                
                cart.setQuantity(newQuantity);
                cartRepository.save(cart);
            } else {
                Cart cart = Cart.builder()
                    .userId(userId)
                    .optionId(optionId)
                    .productId(option.getProduct().getId())
                    .quantity(quantity)
                    .build();
                cartRepository.save(cart);
            }
            
            result.put("success", true);
            result.put("message", "장바구니에 추가되었습니다.");
            return result;
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
            return result;
        }
    }

    @Transactional(readOnly = true)
    public List<CartDTO> getCartItems(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        
        return cartItems.stream()
            .<CartDTO>map(cart -> {
                ProductOption option = productService.getProductOption(cart.getOptionId());
                Product product = option.getProduct();
                
                return CartDTO.builder()
                    .id(cart.getId())
                    .userId(cart.getUserId())
                    .productId(product.getId())
                    .optionId(cart.getOptionId())
                    .name(product.getName())
                    .brand(product.getBrand().toString())
                    .price(product.getPrice())
                    .image(product.getImageUrl())
                    .size(option.getSize().getValue())
                    .quantity(cart.getQuantity())
                    .stockQuantity(option.getStockQuantity())
                    .build();
            })
            .collect(Collectors.toList());
    }
} 
