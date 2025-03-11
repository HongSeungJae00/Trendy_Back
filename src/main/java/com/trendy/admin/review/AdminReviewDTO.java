package com.trendy.admin.review;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class AdminReviewDTO {
    private Long reviewId;
    private Long userId;
    private Long productId;
    private String content;
    private byte[] image;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 기본 생성자
    public AdminReviewDTO() {
    }

    // 모든 필드를 포함한 생성자
    public AdminReviewDTO(Long reviewId, Long userId, Long productId, String content, byte[] image, Timestamp createdAt, Timestamp updatedAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.productId = productId;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
