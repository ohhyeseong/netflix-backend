package com.netflixclone.netflix_backend.config;

import com.netflixclone.netflix_backend.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security를 활성화하고 웹 보안 설정을 구성함을 알립니다.
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 보호 비활성화 (API 서버와 Postman 테스트의 편의를 위해)
                .csrf(AbstractHttpConfigurer::disable)
                // H2 콘솔을 위한 프레임 옵션 비활성화 (개발용)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                // 세션 관리 방식을 STATELESS로 설정 (JWT 사용을 위함)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // HTTP 요청에 대한 접근 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        // H2 콘솔 경로는 누구나 접근 가능
                        .requestMatchers("/h2-console/**").permitAll()
                        // "/signup","/login" 경로로 오는 요청은 모두에게 허용
                        .requestMatchers("/signup","/login").permitAll()
                        // "/api/admin/**" 경로는 ADMIN 역할만 접근 가능
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // 그 외의 모든 요청은 인증된 사용자만 접근 가능
                        .anyRequest().authenticated()
                )
                // 8. 만든 JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
