package com.netflixclone.netflix_backend.repository;

import com.netflixclone.netflix_backend.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
