package com.netflixclone.netflix_backend.filter;

import com.netflixclone.netflix_backend.entity.User;
import com.netflixclone.netflix_backend.repository.UserRepository;
import com.netflixclone.netflix_backend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.Arrays;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. 인증이 필요 없는 경로들을 정의합니다.
        final List<String> permitAllEndpoints = Arrays.asList("/signup", "/login", "/h2-console");

        // 2. 현재 요청의 경로가 인증이 필요 없는 경로 중 하나인지 확인합니다.
        boolean isPermitAllEndpoint = permitAllEndpoints.stream().anyMatch(p -> request.getRequestURI().startsWith(p));

        if (isPermitAllEndpoint) {
            // 3. 인증이 필요 없는 경로라면, 필터의 나머지 로직을 실행하지 않고 즉시 다음 필터로 넘깁니다.
            filterChain.doFilter(request, response);
            return;
        }

        // 1. 요청 헤더에서 Authorization 헤더를 가져옵니다.
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 2. Authorization 헤더가 없거나, 'Bearer '로 시작하지 않으면 요청을 그대로 다음 필터로 넘깁니다.
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 'Bearer ' 부분을 제거하고 실제 토큰만 추출합니다.
        final String token = authorizationHeader.substring(7);

        // 4. 토큰이 유효한지 확인합니다.
        if (jwtUtil.validateToken(token)) {
            // 5. 토큰에서 이메일을 추출합니다.
            String email = jwtUtil.getEmailFromToken(token);
            // 6. 이메일로 사용자를 조회합니다.
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

            // 7. 사용자 정보와 권한을 담아 Spring Security가 이해할 수 있는 Authentication 객체를 만듭니다.
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), null, List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));

            // 8. 인증 정보를 SecurityContext에 저장합니다. 이제 이 요청은 인증된 것으로 간주됩니다.
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        // 9. 다음 필터로 요청을 넘깁니다.
        filterChain.doFilter(request, response);
    }
}