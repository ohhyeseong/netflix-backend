package com.netflixclone.netflix_backend.service;

import com.netflixclone.netflix_backend.dto.LoginRequestDto;
import com.netflixclone.netflix_backend.dto.SignUpUserDto;
import com.netflixclone.netflix_backend.dto.UserInfoResponseDto;
import com.netflixclone.netflix_backend.entity.Role;
import com.netflixclone.netflix_backend.entity.User;
import com.netflixclone.netflix_backend.repository.UserRepository;
import com.netflixclone.netflix_backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // 회원가입 서비스 로직
    public void signUp(SignUpUserDto dto) {

        //1. 이메일 중복 체크
        userRepository.findByEmail(dto.getEmail())
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 가입된 이메일입니다.");
                });

        //2. User 객체 생성 및 정보 설정
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setRole(Role.USER); // 기본 회원가입 하면 USER로 설정 ADMIN은 나중에 추가 로직 만들 예정

        //3. 비밀번호 암호화 하여 설정(중요!!!)
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);

        //4. 데이터베이스에 저장하기
        userRepository.save(user);
    }

    // 로그인 서비스 로직
    public String login(LoginRequestDto dto) {

        //1. 이메일 존재 확인
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));
        
        //2. 비밀번호 확인
        if (!passwordEncoder.matches(dto.getPassword(),user.getPassword())){
            // 일치 않은 경우 예외 던짐
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 4. 로그인 성공 시, JWT 생성하여 반환
        return jwtUtil.createToken(user.getEmail(), user.getRole().name());
    }

    // 사용자 정보 추출 로직
    public UserInfoResponseDto getMyInfo(String email) {// 1. 이메일을 파라미터로 받는다.

        //2. login 메소드에서 썻던 것과 똑같이, 이메일로 User를 찾아온다.
        // 찾지 못하면  예외를 던진다.
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        //3. 찾아온 user객체를 사용해서 UserInfoResponseDto를 만든다.
        //   우리가 만든 정적 팩토리 메소드.
        UserInfoResponseDto responseDto = UserInfoResponseDto.from(user);

        //4. 완성된 DTO를 반환한다.
        return responseDto;
    }

}
