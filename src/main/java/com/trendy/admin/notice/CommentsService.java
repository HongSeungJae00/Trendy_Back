package com.trendy.admin.notice;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private AdminNoticeRepository adminNoticeRepository;

    public Comments addComment(Long inquiryId, Long userId, String content) {
        Comments comment = Comments.builder()
            .inquiryId(inquiryId)
            .content(content)
            .build();
            
        return commentsRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comments> getCommentsByNoticeId(Long inquiryId) {
        return commentsRepository.findByInquiryIdOrderByCreatedAtDesc(inquiryId);
    }
}