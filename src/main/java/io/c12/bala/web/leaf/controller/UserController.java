package io.c12.bala.web.leaf.controller;

import io.c12.bala.web.leaf.exception.UserAlreadyExistsException;
import io.c12.bala.web.leaf.form.RegisterUser;
import io.c12.bala.web.leaf.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static io.c12.bala.web.leaf.constants.AppConstants.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Load home page.. which have a link to Login and Register
     *
     * @return index page
     */
    @GetMapping("/home")
    public String indexPage() {
        log.info("Index page is being loaded . . .");
        return LEAF_APP_INDEX_PAGE;
    }

    /**
     * Load register page form
     *
     * @param model - spring ui model
     * @return register page
     */
    @GetMapping("/register")
    public String registerUser(Model model) {
        log.info("Register a new user . . .");
        // initialize a empty bean to be populated
        model.addAttribute(LEAF_APP_REGISTER_USER_ATTRIBUTE, new RegisterUser());
        return LEAF_APP_REGISTER_PAGE;
    }

    /**
     * Submit registration info, add use to DB
     *
     * @param registerUser bean with registration info
     * @param model        - Spring ui model
     * @return register page
     */
    @PostMapping("/registerSubmit")
    public String registerSubmit(@Valid @ModelAttribute RegisterUser registerUser, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return LEAF_APP_REGISTER_PAGE;
        }
        try {
            userService.saveUser(registerUser);
            model.addAttribute(LEAF_APP_REGISTER_USER_ATTRIBUTE, new RegisterUser());
            model.addAttribute("successMessage", "User added successfully");
        } catch (UserAlreadyExistsException ex) {
            log.warn("Warning message: {}", ex.getMessage());
            model.addAttribute(LEAF_APP_REGISTER_USER_ATTRIBUTE, registerUser);
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("successMessage", null);
        }
        return LEAF_APP_REGISTER_PAGE;
    }

    /**
     * Login page with credentials
     *
     * @return login page
     */
    @GetMapping("/login")
    public String userLogin() {
        log.info("login . . .");
        return LEAF_APP_LOGIN_PAGE;
    }

    /**
     * Home page after successful login
     *
     * @return home page
     */
    @RequestMapping("/")
    public String home() {
        log.info("Loading home page. User logged in.");
        return LEAF_APP_HOME_PAGE;
    }
}
