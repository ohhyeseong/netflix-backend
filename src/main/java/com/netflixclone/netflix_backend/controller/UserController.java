package com.netflixclone.netflix_backend.controller;

import com.netflixclone.netflix_backend.dto.LoginRequestDto;
import com.netflixclone.netflix_backend.dto.SignUpUserDto;
import com.netflixclone.netflix_backend.dto.UserInfoResponseDto;
import com.netflixclone.netflix_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {
//    @GetMapping("/")
//    public String root() {
//        return "redirect:/question/list";
//    }

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpUserDto signUpUserDto) {

        userService.signUp(signUpUserDto);

        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공!");
    }

    // 이메일 중복 되면 예외처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {

        // 1. userService.login()을 호출하고, 반환되는 JWT 토큰을 받는다.
        String token = userService.login(loginRequestDto);

        // 2. 성공 상태(200 OK)와 함께 토큰을 body에 담아 응답한다.
        return ResponseEntity.ok(token);
    }

    // 인증 테스트를 위한 엔드포인트
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("인증된 사용자입니다.");
    }

    // 사용자 정보 조회
    @GetMapping("/api/me")
    public ResponseEntity<UserInfoResponseDto> getMyInfo(@AuthenticationPrincipal String email) {
        //1. @AuthenticationPrincipal로 받아온 email을 서비스로 전달
        UserInfoResponseDto userInfo = userService.getMyInfo(email);
        //2. 서비스로부터 받은 DTO를 응답 본문에 담아 반환
        return ResponseEntity.ok(userInfo);
    }

    // 관리자 전용 테스트 API
    @GetMapping("/api/admin/test")
    public ResponseEntity<String> adminTest() {
        return ResponseEntity.ok("관리자 전용 API 입니다.");
    }
}
