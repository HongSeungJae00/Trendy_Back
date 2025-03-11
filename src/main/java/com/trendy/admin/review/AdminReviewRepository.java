package com.trendy.admin.review;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminReviewRepository extends JpaRepository<AdminReview, Long> {
	// 주문 목록을 생성일(createdAt) 기준 내림차순으로 조회
    List<AdminReview> findAllByOrderByReviewIdDesc();
    List<AdminReview> findAllByOrderByReviewIdAsc();

    // 주문 ID로 주문 조회
    Optional<AdminReview> findByReviewId(Long reviewId);
    
    List<AdminReview> findByUserId(Long userId);
    
    List<AdminReview> findByProductId(Long productId);
    
    // 주문 삭제 (기본 제공)
    void deleteByReviewId(Long reviewId);

    // 다중 주문 삭제
    void deleteAllByReviewIdIn(List<Long> reviewIds);
    
    //옵션과 검색
    // 최신순 및 오래된 순 정렬
    List<AdminReview> findAllByOrderByCreatedAtDesc();
    List<AdminReview> findAllByOrderByCreatedAtAsc();

    // user_id 기준 정렬
    List<AdminReview> findAllByOrderByUserIdAsc();
    List<AdminReview> findAllByOrderByUserIdDesc();

}
