package com.biyesheji.law.controller;

import com.biyesheji.law.service.ExpertUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class ExpertUserController {
    private final ExpertUserService expertUserService;

    public ExpertUserController(ExpertUserService expertUserService) {
        this.expertUserService = expertUserService;
    }
}
