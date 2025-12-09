package com.netflixclone.netflix_backend.controller;

import com.netflixclone.netflix_backend.entity.Question;
import com.netflixclone.netflix_backend.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/question/list")
    public String list(Model model) {
        model.addAttribute("questionList", questionService.getList());
        return "question_list";
    }
}
