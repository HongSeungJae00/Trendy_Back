package com.trendy.admin.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
	// 주문 목록을 생성일(createdAt) 기준 내림차순으로 조회
    List<AdminUser> findAllByOrderByUserIdDesc();
    List<AdminUser> findAllByOrderByUserIdAsc();

    // 주문 ID로 주문 조회
    Optional<AdminUser> findByUserId(Long userId);
    
    // 주문 삭제 (기본 제공)
    void deleteByUserId(Long userId);

    // 다중 주문 삭제
    void deleteAllByUserIdIn(List<Long> userIds);
    
    //옵션과 검색
    // 최신순 및 오래된 순 정렬
    List<AdminUser> findAllByOrderByCreatedAtDesc();
    List<AdminUser> findAllByOrderByCreatedAtAsc();

}
