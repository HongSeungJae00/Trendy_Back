package com.trendy.mypage.address;

import org.springframework.stereotype.Service;

import com.trendy.login.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AddressService {

    private final UserRepository userRepository;

    public boolean updateAddress(Long userId, String address) {
        return userRepository.findById(userId)
                .map(user -> {
                    if (user.getAddress1() == null || user.getAddress1().isEmpty()) {
                        user.setAddress1(address);
                    } else if (user.getAddress2() == null || user.getAddress2().isEmpty()) {
                        user.setAddress2(address);
                    } else if (user.getAddress3() == null || user.getAddress3().isEmpty()) {
                        user.setAddress3(address);
                    } else {
                        throw new IllegalStateException("모든 주소 필드가 이미 사용 중입니다.");
                    }
                    userRepository.save(user);
                    return true;
                })
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }
}

