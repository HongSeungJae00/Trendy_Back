package com.trendy.admin.notice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminNoticeRepository extends JpaRepository<AdminNotice, Long> {
	
    // 게시물 ID로 게시물 조회 (기본 제공)
    Optional<AdminNotice> findByInquiryId(Long inquiryId);

    // 게시물 삭제 (기본 제공)
    void deleteById(Long inquiryId);
    
    void deleteAllByInquiryIdIn(List<Long> inquiryIds);
    
    @Query("SELECT COALESCE(MAX(b.inquiryId), 0) FROM AdminNotice b")
    int findMaxInquiryId();
    
    // 게시물 ID 내림차순 정렬
    List<AdminNotice> findAllByOrderByInquiryIdDesc();

    // 특정 ID로 조회
    @Query("SELECT n FROM AdminNotice n WHERE n.inquiryId = :inquiryId")
    List<AdminNotice> findAllByInquiryId(@Param("inquiryId") Long inquiryId);
    
    Page<AdminNotice> findByCategory(String category, Pageable pageable);
    Page<AdminNotice> findByCategoryContaining(String category, Pageable pageable);
    Page<AdminNotice> findByCategory(AdminNotice.Category category, Pageable pageable);
}