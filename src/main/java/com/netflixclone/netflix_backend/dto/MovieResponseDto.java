package com.netflixclone.netflix_backend.dto;

import com.netflixclone.netflix_backend.entity.Genre;
import com.netflixclone.netflix_backend.entity.Movie;
import lombok.Getter;

@Getter
public class MovieResponseDto {
    private final Long id;
    private final String title;
    private final String description;
    private final Genre genre;

    public MovieResponseDto(Movie movie){
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.description = movie.getDescription();
        this.genre = movie.getGenre();
    }

    public static MovieResponseDto from(Movie movie) {
        return new MovieResponseDto(movie);
    }
}
