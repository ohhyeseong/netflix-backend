package com.netflixclone.netflix_backend.controller;

import com.netflixclone.netflix_backend.dto.MovieRequestDto;
import com.netflixclone.netflix_backend.dto.MovieResponseDto;
import com.netflixclone.netflix_backend.dto.MovieUpdateRequestDto;
import com.netflixclone.netflix_backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;


    @PostMapping("/admin/movies")
    public ResponseEntity<String> addMovie(@RequestBody MovieRequestDto dto) {

        movieService.addMovie(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("영화가 성공적으로 등록되었습니다!");
    }

    @GetMapping("/movies")
    public ResponseEntity<List<MovieResponseDto>> findAllMovies() {

        List<MovieResponseDto> movies = movieService.findAllMovies();

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<MovieResponseDto> findMovieById(@PathVariable Long id) {

        MovieResponseDto movie = movieService.findMovieById(id);

        return ResponseEntity.ok(movie);
    }

    @PutMapping("/admin/movies/{id}")
    public ResponseEntity<MovieResponseDto> updateMovie(@PathVariable Long id, @RequestBody MovieUpdateRequestDto dto) {
        MovieResponseDto updateMovie = movieService.updateMovie(id, dto);
        return ResponseEntity.ok(updateMovie);
    }

    @DeleteMapping("/admin/movies/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
