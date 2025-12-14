package com.netflixclone.netflix_backend.config;

import com.netflixclone.netflix_backend.exception.CustomAccessDeniedHandler;
import com.netflixclone.netflix_backend.exception.CustomAuthenticationEntryPoint;
import com.netflixclone.netflix_backend.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // Spring Security를 활성화하고 웹 보안 설정을 구성함을 알립니다.
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 1. CORS 설정을 위한 Bean을 추가합니다.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 출처(Origin) 목록 - 프론트엔드 개발 서버 주소
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173")); // React, Vite 기본 포트

        // 허용할 HTTP 메소드 목록
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 허용할 헤더 목록
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        // 자격 증명(쿠키 등)을 허용할지 여부
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로("/**")에 대해 위에서 정의한 CORS 설정을 적용
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정 적용
                .cors(withDefaults())
                // CSRF 보호 비활성화 (API 서버와 Postman 테스트의 편의를 위해)
                .csrf(AbstractHttpConfigurer::disable)
                // H2 콘솔을 위한 프레임 옵션 비활성화 (개발용)
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                // 세션 관리 방식을 STATELESS로 설정 (JWT 사용을 위함)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 세션 예외 처리 핸들러 등록(내가 원하는 예외 처리{메세지 같은거} 등록)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                // HTTP 요청에 대한 접근 권한 설정
                .authorizeHttpRequests(authorize -> authorize
                        // 영화 리스트 조회 경로는 누구나 접근 가능
                        .requestMatchers(HttpMethod.GET,"/api/movies").permitAll()
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
