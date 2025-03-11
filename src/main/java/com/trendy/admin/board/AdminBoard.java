package com.trendy.admin.board;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Notices_Events")
public class AdminBoard {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "notice_event_id")
	    private int noticeEventId;
	    
	    @Column(name = "user_id", nullable = true)
	    private Long userId;
	    
	    private String username;
	    private String type;
	    private String title;
	    private String content;
	    @CreationTimestamp
	    @Column(name = "created_at", nullable = false, updatable = false)
	    private Timestamp createdAt;
	    
	    public String getUsername() {
	    	return this.username;
	    }
	    public void setUsername(String username) {
	    	this.username=username;
	    }
	    // public int getUser_id() {
	    // 	return this.id;
	    // }
	    public void setNotice_event_id(int noticeEventId) {
	        this.noticeEventId = noticeEventId;
	    }
	    public int getNotice_event_id() {
	        return this.noticeEventId;
	    }
	    public void setType(String type) {
	        this.type = type;
	    }
	    public String getType() {
	        return this.type;
	    }
	    public void setTitle(String title) {
	    	this.title = title;
	    }
	    public String getTitle() {
	    	return this.title;
	    }
	    public void setContent(String content) {
	    	this.content = content;
	    }
	    public String getContent() {
	    	return this.content;
	    }
}