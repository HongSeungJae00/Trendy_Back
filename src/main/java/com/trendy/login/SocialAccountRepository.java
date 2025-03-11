package com.trendy.login;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    // 이메일로 계정 조회
    Optional<SocialAccount> findByEmail(String email);

    // SocialId와 Provider로 계정 조회
    Optional<SocialAccount> findBySocialIdAndProvider(String socialId, Provider provider);
}
