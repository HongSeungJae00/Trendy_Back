package com.trendy.login;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserUtil {

    // 현재 로그인된 사용자 정보 가져오기
    public static CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    // 현재 사용자 정보 업데이트
    public static void updateCurrentUser(CustomUserDetails updatedUserDetails) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(
                updatedUserDetails,
                null,
                updatedUserDetails.getAuthorities()
            );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}