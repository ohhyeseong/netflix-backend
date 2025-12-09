package com.netflixclone.netflix_backend.repository;

import com.netflixclone.netflix_backend.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
