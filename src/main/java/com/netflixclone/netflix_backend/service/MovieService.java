package com.netflixclone.netflix_backend.service;

import com.netflixclone.netflix_backend.dto.MovieRequestDto;
import com.netflixclone.netflix_backend.dto.MovieResponseDto;
import com.netflixclone.netflix_backend.entity.Movie;
import com.netflixclone.netflix_backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.function.LongFunction;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    // 영화 생성(운영자)
    public void addMovie(MovieRequestDto dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setGenre(dto.getGenre());
        movieRepository.save(movie);
    }
    // 영화 전체 조회
//          리스트 객체를 모두 가져와서 stream()을 사용해 하나씩 옮겨준다.
    public List<MovieResponseDto> findAllMovies(){
        return movieRepository.findAll().stream()
                // map(stream된 객체들 한개씩 from 메서드를 실행해서 타이틀 설명 종류 정보 표시된 dto를 변환해준다.
                .map(MovieResponseDto::from)
                // 변환된걸 다시 새롭게 리스트로 만든다.
                .toList();
    }

    // 특정 영화 조회(영화 상세보기)
    public MovieResponseDto findMovieById(Long id){
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("영화가 등록되어있지 않습니다."));

        MovieResponseDto responseDto = MovieResponseDto.from(movie);

        return responseDto;

    }
}
