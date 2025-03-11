package com.trendy.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 기존 서비스 메서드 유지

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 기타 메서드들...
} 