package com.trendy.admin.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class AdminNoticeService {
	@Autowired
	private HttpSession session;
	@Autowired
	private AdminNoticeRepository noticeRepository;
	

	// [목록 페이지]
	public List<AdminNotice> getAllNotices() {
		return noticeRepository.findAllByOrderByInquiryIdDesc();
	}
	
	// [수정 페이지]
	// 게시물 조회
	 public AdminNoticeDTO getNoticeDetail(Long inquiryId) {
	        AdminNotice notice = noticeRepository.findById(inquiryId).orElseThrow(() -> new RuntimeException("Inquiry not found"));
	        return AdminNoticeDTO.builder()
	                .title(notice.getTitle())
	                .content(notice.getContent())
	                .category(notice.getCategory().toString())
	                .build();
	    }
	
	// 게시물 삭제
	public void deleteNotice(Long inquiryId) {
		noticeRepository.deleteById(inquiryId);
	}
	
	
	// 다중 게시물 삭제
	public void deleteMultipleNotices(List<Long> inquiryIds) {
		if (inquiryIds == null || inquiryIds.isEmpty()) {
            throw new IllegalArgumentException("삭제할 게시물이 선택되지 않았습니다.");
        }
		noticeRepository.deleteAllByInquiryIdIn(inquiryIds);
	}
	/*
	 // 게시물 등록
    public void registerNotice(String type, String title, String content) {
    	AdminNotice newNotice = new AdminNotice();
        newNotice.setUsername("관리자");
        newNotice.setType(type);
        newNotice.setTitle(title);
        newNotice.setContent(content);
        newNotice.setId(1);

        //newBoard.setType("일반"); // 기본 유형 설정
        noticeRepository.save(newNotice);
    }
    */
	public Page<AdminNoticeDTO> getAllNotices(String sort, Pageable pageable) {
        Sort sorting = createSort(sort);
        Page<AdminNotice> noticePage = noticeRepository.findAll(
            PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting)
        );
        return noticePage.map(this::convertToDTO);
    }
    
    public Page<AdminNoticeDTO> getNoticesByCategory(String category, String sort, Pageable pageable) {
    	Sort sorting = createSort(sort);
        try {
            // String을 enum으로 변환
            AdminNotice.Category categoryEnum = AdminNotice.Category.valueOf(category.toUpperCase());
            Page<AdminNotice> noticePage = noticeRepository.findByCategory(
                categoryEnum,
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sorting)
            );
            return noticePage.map(notice -> convertToDTO(notice));
        } catch (IllegalArgumentException e) {
            // 잘못된 카테고리 값이 입력된 경우 빈 결과 반환
            return Page.empty(pageable);
        }
    }
    
    private Sort createSort(String sort) {
        Sort.Direction direction = "asc".equalsIgnoreCase(sort) ? 
            Sort.Direction.ASC : Sort.Direction.DESC;
        return Sort.by(direction, "createdAt");
    }
    
    private AdminNoticeDTO convertToDTO(AdminNotice notice) {
        return AdminNoticeDTO.builder()
            .inquiryId(notice.getInquiryId())
            .category(notice.getCategory().name())
            .title(notice.getTitle())
            .content(notice.getContent())
            .createdAt(notice.getCreatedAt())
            .updatedAt(notice.getUpdatedAt())
            .userId(notice.getUserId())
            .productId(notice.getProductId())
            .response(notice.getResponse())
            .build();
    }
}