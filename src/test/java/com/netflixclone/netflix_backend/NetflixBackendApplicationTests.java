package com.netflixclone.netflix_backend;

import com.netflixclone.netflix_backend.entity.Question;
import com.netflixclone.netflix_backend.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class NetflixBackendApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void testJpa() {
		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1); // DB에 첫 번째 데이터 저장

		Question q2 = new Question();
		q2.setSubject("스프링 부트 질문");
		q2.setContent("JPA가 잘 안됩니다.");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2); // DB에 두 번째 데이터 저장
		}
	}


