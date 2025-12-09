package com.netflixclone.netflix_backend.dto;

import com.netflixclone.netflix_backend.entity.Role;
import com.netflixclone.netflix_backend.entity.User;
import lombok.Getter;

@Getter
public class UserInfoResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final Role role;

    // 생성자는 private 으로 막아서, 오직 아래의 from 메소드를 통해서만 객체를 만들도록 강제
    private UserInfoResponseDto(User user){
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.role = user.getRole();
    }

    // User 엔티티를 받아서 DTO를 생성하는 정적 팩토리 메소드
    public static UserInfoResponseDto from(User user) {
        return new UserInfoResponseDto(user);
    }

}
