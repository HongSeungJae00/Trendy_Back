package com.trendy.mypage.CustomerCenter;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeEventRepository extends JpaRepository<NoticeEvent, Long> {
    List<NoticeEvent> findByType(String type);
    List<NoticeEvent> findAll();
    List<NoticeEvent> findByTitleContaining(String query);
    }
