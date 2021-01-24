package io.c12.bala.web.leaf.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Log4j2
@Controller
public class UserController {

    @GetMapping("/")
    public String indexPage() {
        log.info("Index page is being loaded . . .");
        return "index";
    }

    @GetMapping("/register")
    public String registerUser() {
        log.info("Register a new user . . .");
        return "register";
    }

    @GetMapping("/login")
    public String userLogin() {
        log.info("login . . .");
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        log.info("login . . .");
        return "home";
    }
}
