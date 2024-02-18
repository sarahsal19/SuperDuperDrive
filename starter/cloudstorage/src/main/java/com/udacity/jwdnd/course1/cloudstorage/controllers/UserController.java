package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String signUp(@ModelAttribute("user") User user, Model model) {
        addFirstAndLastNameToModel(user,model);
        addUserAndPasswordToModel(user,model);

        if (userService.createUser(user, model) > 0)
            return "login";

        return "signup";
    }

    @GetMapping("/signup")
    public String signUpView(@ModelAttribute("user") User user) {
        return "signup";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model) {
        addUserAndPasswordToModel(user,model);
        return "login";
    }

    @GetMapping("/login")
    public String loginView(@ModelAttribute("user") User user) {
        return "login";
    }

    private void addUserAndPasswordToModel(User user, Model model){
        model.addAttribute("username", user.getUsername());
        model.addAttribute("password", user.getPassword());
    }

    private void addFirstAndLastNameToModel(User user, Model model){
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
    }
}

