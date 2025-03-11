package com.trendy.admin.notice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentsController {
    
    private final CommentsService commentsService;

    @PostMapping("/comments")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
        try {
            System.out.println("댓글 요청 받음: " + request);
            
            Comments savedComment = commentsService.addComment(
                request.getInquiryId(),
                request.getUserId(),
                request.getContent()
            );
            
            // DTO로 변환하여 반환
            return ResponseEntity.ok(CommentResponseDTO.from(savedComment));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
