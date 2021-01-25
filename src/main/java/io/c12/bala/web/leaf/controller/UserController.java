package io.c12.bala.web.leaf.controller;

import io.c12.bala.web.leaf.form.RegisterUser;
import io.c12.bala.web.leaf.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Log4j2
@Controller
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping({"/", "/index"})
    public String indexPage() {
        log.info("Index page is being loaded . . .");
        return "index";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        log.info("Register a new user . . .");
        // initialize a empty bean to be populated
        model.addAttribute("registerUser", new RegisterUser());
        return "register";
    }

    @PostMapping("/registerSubmit")
    public String registerSubmit(@Valid @ModelAttribute RegisterUser registerUser, Model model) {
        userService.saveUser(registerUser);
        model.addAttribute("registerUser", new RegisterUser());
        model.addAttribute("successMessage", "User added successfully");
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
