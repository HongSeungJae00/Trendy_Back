package com.trendy.config;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.trendy.security.JwtAuthenticationFilter;
import com.trendy.security.JwtTokenProvider;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtAuthFilter = new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
        
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // CORS 설정 추가
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers(
                    "/api/auth/login", 
                    "/api/auth/oauth2/**",
                    "/api/admin/**",  // admin 경로 추가
                    "/oauth2/authorization/**", 
                    "/login/oauth2/code/*",
                    "/templates/**",
                    "/health",
                    "/actuator/**",
                    "/actuator/health/**",
                    "/actuator/info/**",
                    "/error",
                    "/api/public/**",
                    "/",
                    "/static/**",
                    "/uploads/**",
                    "/images/**",
                    "/api/products",
                    "/api/products/**",
                    "/api/review/product/{productId}"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()  // GET 요청만 허용
                .requestMatchers(HttpMethod.GET, "/api/review/product/{productId}").permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()  // OPTIONS 요청 허용
                .requestMatchers("/api/cart/**").authenticated()  // 장바구니 API 인증 필요
                .anyRequest().authenticated();
            })
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/api/auth/oauth2/login")
                .successHandler(successHandler())
                .failureHandler(failureHandler())
            )
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint((request, response, authException) -> {
                    logger.error("Unauthorized error: {}", authException.getMessage());
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(
                        String.format(
                            "{\"error\": \"인증 실패\", \"message\": \"%s\"}",
                            authException.getMessage()
                        )
                    );
                })
            );

        logger.info("Security filter chain configured successfully");
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService)
               .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void handle(
                jakarta.servlet.http.HttpServletRequest request,
                jakarta.servlet.http.HttpServletResponse response,
                org.springframework.security.core.Authentication authentication
            ) throws java.io.IOException, jakarta.servlet.ServletException {
                String token = jwtTokenProvider.createToken(authentication.getName());

                // Get the intended destination URL from the session
                String targetUrl = (String) request.getSession().getAttribute("REDIRECT_URI");
                if (targetUrl == null || targetUrl.isEmpty()) {
                    targetUrl = "https://trendy.kg.kr"; // Redirect to login page with token
                }

                // Append token to the redirect URL
                String redirectUrl = targetUrl + (targetUrl.contains("?") ? "&" : "?") + "token=" + token;

                logger.info("Authentication succeeded for user: " + authentication.getName() + 
                            ". Redirecting to: " + redirectUrl);

                // Redirect to the frontend application with token
                response.sendRedirect(redirectUrl);
            }
        };
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(
                jakarta.servlet.http.HttpServletRequest request,
                jakarta.servlet.http.HttpServletResponse response,
                org.springframework.security.core.AuthenticationException exception
            ) throws java.io.IOException, jakarta.servlet.ServletException {
                logger.error("Authentication failed: " + exception.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(
                    String.format(
                        "{\"error\": \"인증 실패\", \"message\": \"%s\"}",
                        exception.getMessage()
                    )
                );
            }
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://trendy.kg.kr", "https://api.trendy.kg.kr"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
