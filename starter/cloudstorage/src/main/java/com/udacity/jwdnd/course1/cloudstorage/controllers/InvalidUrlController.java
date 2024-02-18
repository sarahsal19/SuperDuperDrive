package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvalidUrlController implements ErrorController {

    @GetMapping("/error")
    public String handleInvalidUrl(Model model){
        model.addAttribute("home",true);
        return "invalidurl";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
