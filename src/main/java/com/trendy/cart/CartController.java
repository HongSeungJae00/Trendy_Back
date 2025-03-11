package com.trendy.cart;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trendy.login.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestBody Map<String, Object> request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "로그인이 필요합니다."));
        }

        try {
            Long optionId = Long.parseLong(request.get("optionId").toString());
            int quantity = Integer.parseInt(request.get("quantity").toString());

            if (quantity <= 0) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "수량은 1개 이상이어야 합니다."));
            }

            Map<String, Object> result = cartService.addToCart(
                userDetails.getUserId(), 
                optionId,
                quantity
            );
            
            return ResponseEntity.ok(result);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", "잘못된 형식의 파라미터입니다."));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }


    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getCartItems(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "로그인이 필요합니다."));
        }
    
        try {
            List<CartDTO> cartItems = cartService.getCartItems(userDetails.getUserId());
            return ResponseEntity.ok(Map.of(
                "success", true,
                "items", cartItems  // "data" 대신 "items"로 변경
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
} 