package com.trendy.login;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.trendy.security.JwtTokenProvider;

import jakarta.annotation.PostConstruct;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SocialAccountRepository socialAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        logger.info("CustomOAuth2UserService initialized with socialAccountRepository: {}", 
            socialAccountRepository != null ? "injected" : "not injected");
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String username = oauth2User.getAttribute("email");
        String name = oauth2User.getAttribute("name");
        
        // JWT 토큰 생성
        String jwtToken = jwtTokenProvider.createToken(username);
        logger.info("JWT token created: {}", jwtToken);
        
        try {
            // users 테이블에 저장
            logger.info("Attempting to save user - Email: {}, Name: {}", username, name);

            // 동일한 email이 존재하는지 확인
            Optional<User> existingUser = userRepository.findByEmail(username);

            if (existingUser.isPresent()) {
                logger.info("User with email {} already exists. Skipping insertion.", username);
            } else {
                // 새로운 사용자 추가
                User user = new User();
                user.setEmail(username);
                user.setUsername(name);
                userRepository.save(user);
                logger.info("User saved successfully");
            }

            
            // social_accounts에 저장
            SocialAccount socialAccount = new SocialAccount();
            socialAccount.setEmail(username);
            socialAccount.setProvider(Provider.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase()));
            socialAccount.setAccessToken(jwtToken);
            socialAccountRepository.save(socialAccount);
            logger.info("Social account saved successfully");
            
            // attributes에 JWT 토큰 추가
            Map<String, Object> attributes = new HashMap<>(oauth2User.getAttributes());
            attributes.put("token", jwtToken);
            
            return new DefaultOAuth2User(
                oauth2User.getAuthorities(),
                attributes,
                "email"
            );
        } catch (Exception e) {
            logger.error("Error in OAuth process: {}", e.getMessage(), e);
            throw e;
        }
    }
}
