package com.trendy.admin.board;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminBoardRepository extends JpaRepository<AdminBoard, Integer> {
	List<AdminBoard> findAllByOrderByNoticeEventIdDesc();
	
    // 게시물 ID로 게시물 조회 (기본 제공)
    Optional<AdminBoard> findBynoticeEventId(int noticeEventId);
    List<AdminBoard> findAllByNoticeEventId(int noticeEventId);

    // 게시물 삭제 (기본 제공)
    void deleteById(int noticeEventId);
    
    void deleteAllByNoticeEventIdIn(List<Integer> noticeEventIds);
    
    @Query("SELECT COALESCE(MAX(b.noticeEventId), 0) FROM AdminBoard b")
    int findMaxnoticeEventId();
}