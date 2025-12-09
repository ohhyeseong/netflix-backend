package com.netflixclone.netflix_backend.controller;

import com.netflixclone.netflix_backend.dto.MovieRequestDto;
import com.netflixclone.netflix_backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
