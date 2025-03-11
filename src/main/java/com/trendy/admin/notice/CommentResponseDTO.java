package com.trendy.admin.notice;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDTO {
    private Long commentId;
    private Long inquiryId;
    private String content;
    private LocalDateTime createdAt;

    public static CommentResponseDTO from(Comments comment) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setCommentId(comment.getId());
        dto.setInquiryId(comment.getInquiryId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }
}