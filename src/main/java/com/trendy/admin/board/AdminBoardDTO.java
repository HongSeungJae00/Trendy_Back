package com.trendy.admin.board;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminBoardDTO {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_event_id")
    private int noticeEventId;
    
    @Column(name = "user_id")
    private int id;
    
    private String username;
    private String type;
    private String title;
    private String content;
    
    public String getUsername() {
    	return this.username;
    }
    public void setUsername(String username) {
    	this.username=username;
    }
    public int getUser_id() {
    	return this.id;
    }
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