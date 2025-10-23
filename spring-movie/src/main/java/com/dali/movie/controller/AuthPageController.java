package com.dali.movie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController {

    @GetMapping({"/", "/login", "/register"})
    public String showAuthPage() {
        return "forward:/首页.html";
    }
}
