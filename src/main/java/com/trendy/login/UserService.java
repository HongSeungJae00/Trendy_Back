package com.trendy.login;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 현재 로그인된 사용자 정보 가져오기
    public User getCurrentUserEntity() {
        CustomUserDetails currentUser = UserUtil.getCurrentUser();
        return userRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    // 사용자 정보 업데이트
    public void updateUserProfile(String newEmail, String newProfileImageUrl) {
        User currentUser = getCurrentUserEntity();
        currentUser.setEmail(newEmail);
        currentUser.setProfileImageUrl(newProfileImageUrl);
        userRepository.save(currentUser);

        // SecurityContext에 업데이트된 사용자 정보 반영
        CustomUserDetails updatedDetails = new CustomUserDetails(currentUser);
        UserUtil.updateCurrentUser(updatedDetails);
    }
}