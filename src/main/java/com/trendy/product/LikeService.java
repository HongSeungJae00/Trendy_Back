package com.trendy.product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class LikeService {
    private final LikeRepository likeRepository;
    private final ProductRepository productRepository;

    public LikeService(LikeRepository likeRepository, ProductRepository productRepository) {
        this.likeRepository = likeRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Map<String, Object> toggleLike(Long userId, Long productId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            log.info("토글 좋아요 시작 - userId: {}, productId: {}", userId, productId);
            
            if (userId == null) {
                log.warn("사용자 ID가 null입니다");
                result.put("success", false);
                result.put("message", "사용자 인증이 필요합니다");
                return result;
            }

            // 상품 존재 여부 먼저 확인
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + productId));
            
            log.info("상품 조회 성공 - 현재 좋아요 수: {}", product.getLikeCount());

            Optional<Like> existingLike = likeRepository.findByUserIdAndProductId(userId, productId);
            log.info("기존 좋아요 여부: {}", existingLike.isPresent());

            boolean isLiked;
            if (existingLike.isEmpty()) {
                // 좋아요 추가
                Like like = new Like(userId, productId);
                likeRepository.save(like);
                productRepository.incrementLikeCount(productId);
                isLiked = true;
                log.info("좋아요 추가 완료");
            } else {
                // 좋아요 취소
                likeRepository.delete(existingLike.get());
                productRepository.decrementLikeCount(productId);
                isLiked = false;
                log.info("좋아요 취소 완료");
            }

            int updatedLikeCount = productRepository.getLikeCount(productId);
            log.info("업데이트된 좋아요 수: {}", updatedLikeCount);
            
            result.put("success", true);
            result.put("liked", isLiked);
            result.put("likeCount", updatedLikeCount);
            
            return result;
            
        } catch (Exception e) {
            log.error("좋아요 처리 중 에러 발생", e);
            result.put("success", false);
            result.put("message", e.getMessage());
            throw new RuntimeException("좋아요 처리 중 에러가 발생했습니다: " + e.getMessage());
        }
    }
}

