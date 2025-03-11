package com.trendy.admin.notice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
	List<Comments> findByInquiryIdOrderByCreatedAtDesc(Long inquiryId);
}