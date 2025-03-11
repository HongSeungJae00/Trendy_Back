package com.trendy.login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {
    private String email;
    private String profileImageUrl;
    private Long userId;

    public CustomUserDetails(User user) {
        super(user.getEmail(), user.getPassword(), 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        this.email = user.getEmail();
        this.profileImageUrl = user.getProfileImageUrl();
        this.userId = user.getUserId();
    }

    public String getEmail() {
        return email;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public Long getUserId() {
        return userId;
    }
}