package com.trendy.login;

public enum Provider {
    GOOGLE,
    NAVER,
    KAKAO;

    public static Provider fromString(String provider) {
        if (provider == null || provider.isEmpty()) {
            throw new IllegalArgumentException("Provider cannot be null or empty");
        }
    
        try {
            return Provider.valueOf(provider.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid provider: " + provider, e);
        }
    }
    
}
