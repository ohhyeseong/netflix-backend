package com.netflixclone.netflix_backend.dto;

import com.netflixclone.netflix_backend.entity.Genre;
import lombok.Getter;

@Getter
public class MovieRequestDto {
    private final String title;
    private final String description;
    private final Genre genre;

    public MovieRequestDto(String title, String description, Genre genre){
        this.title = title;
        this.description = description;
        this.genre = genre;
    }
}
