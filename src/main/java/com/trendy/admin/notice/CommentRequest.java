package com.trendy.admin.notice;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
class CommentRequest {
    private Long inquiryId;  // noticeId에서 inquiryId로 변경
    private Long userId;
    private String content;
}
