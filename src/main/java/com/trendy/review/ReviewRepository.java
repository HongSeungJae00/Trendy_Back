package com.trendy.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 사용자ID와 리뷰ID로 리뷰 찾기 (권한 확인용)
    Optional<Review> findByReviewIdAndUserId(Long reviewId, Long userId);
    
    // 특정 상품의 리뷰 목록 조회
    List<Review> findByProductId(Long productId);

    List<Review> findByUserId(Long userId);

    // 사용자별 리뷰 목록을 최신순으로 조회
    List<Review> findByUserIdOrderByCreatedAtDesc(Long userId);
}