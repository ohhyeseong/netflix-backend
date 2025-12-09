package com.netflixclone.netflix_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpUserDto {
    private String email;
    private String password;
    private String name;
}
