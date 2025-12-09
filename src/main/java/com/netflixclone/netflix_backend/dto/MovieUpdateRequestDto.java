package com.netflixclone.netflix_backend.dto;

import com.netflixclone.netflix_backend.entity.Genre;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovieUpdateRequestDto {
    private String title;
    private String description;
    private Genre genre;
}
