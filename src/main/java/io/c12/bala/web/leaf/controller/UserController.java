package io.c12.bala.web.leaf.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/home")
    public String home() {
        log.info("Loading home page . . .");
        return "home";
    }
}
