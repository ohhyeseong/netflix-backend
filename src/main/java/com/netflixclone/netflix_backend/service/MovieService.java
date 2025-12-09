package com.netflixclone.netflix_backend.service;

import com.netflixclone.netflix_backend.dto.MovieRequestDto;
import com.netflixclone.netflix_backend.entity.Movie;
import com.netflixclone.netflix_backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public void addMovie(MovieRequestDto dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setGenre(dto.getGenre());
        movieRepository.save(movie);
    }
}
