package com.netflixclone.netflix_backend.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice : 프로젝트 전역의 @RestController에서 발생하는 예외를 처리
@RestControllerAdvice
public class GlobalExceptionHandler {
    // @ExceptionHandler(IllegalArgumentException.class) : IllegalArgumentException이 발생했을 때 이 메소드를 실행
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.NOT_FOUND.value()); // 404
        body.put("error", "Not Found");
        body.put("message", ex.getMessage()); // 서비스 계층에서 던진 메시지를 그대로 사용

        // "해당 ID의 영화를 찾을 수 없습니다" 와 같은 예외는 409 Conflict 보다는 404 Not Found가 더 적절합니다.
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}
