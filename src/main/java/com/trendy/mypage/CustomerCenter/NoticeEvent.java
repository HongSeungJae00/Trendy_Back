package com.trendy.mypage.CustomerCenter;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "notices_events")
public class NoticeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_event_id")
    private Long noticeEventId;

    @Column(name = "user_id")
    private Long userId;


    private String type; // 공지/이벤트 타입
    private String title; // 제목
    private String content; // 내용
    private int viewCount; // 조회수
    private String imageUrl; // 이미지 URL
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    
    public Long getNoticeEventId() {
        return noticeEventId;
    }

    public void setNoticeEventId(Long noticeEventId) {
        this.noticeEventId = noticeEventId;
    }
    
    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
