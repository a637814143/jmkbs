package com.example.movie.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AuthPageController {

    @GetMapping({"/", "/auth", "/auth/", "/auth/login", "/login"})
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping({"/auth/register", "/register"})
    public String registerPage() {
        return "auth/register";
    }
}
