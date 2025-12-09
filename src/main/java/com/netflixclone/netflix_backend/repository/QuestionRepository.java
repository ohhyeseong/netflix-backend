package com.netflixclone.netflix_backend.repository;

import com.netflixclone.netflix_backend.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

}
