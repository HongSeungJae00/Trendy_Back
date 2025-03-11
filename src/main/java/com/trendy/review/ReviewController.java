package com.trendy.review;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.trendy.login.CustomUserDetails;
import com.trendy.order.Order;
import com.trendy.order.OrderRepository;
import com.trendy.product.Product;
import com.trendy.product.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/review")
@Slf4j  // Lombok annotation for logging
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<?> showReviewForm(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getUserId();
        
        List<Order> completedOrders = orderRepository.findByUserIdAndStatus(userId, Order.OrderStatus.completed);
        List<Product> availableProducts = new ArrayList<>();
        
        if (!completedOrders.isEmpty()) {
            Order firstOrder = completedOrders.get(0);
            Product defaultProduct = productService.getProductById(firstOrder.getProductId());
            
            for (Order order : completedOrders) {
                Product product = productService.getProductById(order.getProductId());
                if (product != null) {
                    availableProducts.add(product);
                }
            }
        }
        
        List<Review> reviews = reviewRepository.findByUserId(userId);
        
        Map<String, Object> response = new HashMap<>();
        response.put("availableProducts", availableProducts);
        response.put("reviews", reviews);
        response.put("userId", userId);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> saveReview(
            @RequestParam("productId") Long productId,
            @RequestParam("content") String content,
            @RequestParam("image") MultipartFile image,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) throws IOException {
        if (image != null && !image.isEmpty() && image.getSize() > 5 * 1024 * 1024) {
            return ResponseEntity.badRequest().body("이미지 크기는 5MB를 초과할 수 없습니다.");
        }
        
        Review review = new Review();
        review.setProductId(productId);
        review.setContent(content);
        review.setUserId(userDetails.getUserId());

        if (!image.isEmpty()) {
            review.setImage(image.getBytes());
        }

        reviewRepository.save(review);
        return ResponseEntity.ok().body("리뷰가 성공적으로 저장되었습니다.");
    }

    @PostMapping("/delete/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Long reviewId,
                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Review review = reviewRepository.findByReviewIdAndUserId(reviewId, userDetails.getUserId())
                .orElseThrow(() -> new RuntimeException("리뷰를 찾을 수 없거나 권한이 없습니다."));
            
            reviewRepository.delete(review);
            return ResponseEntity.ok().body("리뷰가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("리뷰 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductReviews(@PathVariable(name = "productId") Long productId) {
        log.info("Received request for product ID: {}", productId);
        try {
            List<Review> productReviews = reviewRepository.findByProductId(productId);
            Product product = productService.getProductById(productId);
            
            if (product == null) {
                return ResponseEntity.notFound().build();
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("product", product);
            response.put("reviews", productReviews);
            response.put("totalReviews", productReviews.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("리뷰 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserReviews(@AuthenticationPrincipal CustomUserDetails userDetails) {
        try {
            Long userId = userDetails.getUserId();
            List<Review> userReviews = reviewRepository.findByUserIdOrderByCreatedAtDesc(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("reviews", userReviews);
            response.put("totalReviews", userReviews.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body("리뷰 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
