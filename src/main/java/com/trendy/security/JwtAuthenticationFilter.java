package com.trendy.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        // OPTIONS 요청 필터 제외
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }

        // GET 요청(상품 조회, 검색 등) 필터 제외
        if (path.startsWith("/api/products") && "GET".equalsIgnoreCase(method)) {
            return true;
        }

        // OAuth2 로그인 API 요청 필터 제외
        if (path.startsWith("/oauth2/authorization") || path.startsWith("/api/auth/oauth2/success")) {
            return true;
        }

        return path.startsWith("/api/products/filter") ||
               path.startsWith("/api/products/search") ||
               path.startsWith("/api/auth/login") ||
               path.startsWith("/health") ||
               path.startsWith("/error") ||
               path.startsWith("/api/public/") ||
               path.equals("/") ||
               path.startsWith("/static/") ||
               path.startsWith("/uploads/") ||
               path.startsWith("/images/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            logger.info("✅ JWT is valid, processing authentication for token");

            String username = jwtTokenProvider.getUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.info("✅ User authenticated: {}", username);
        } else {
            logger.warn("⚠ JWT is invalid or missing: {}", token);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
