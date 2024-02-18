package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.impl.CredentialsServiceImpl;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
@Controller
@AllArgsConstructor
public class CredentialsController {
    private final CredentialsServiceImpl credentialService;

    @PostMapping("/add-credential")
    public String addUpdateCredential(@ModelAttribute("credential") Credential credential, Authentication authentication, Model model){
        credentialService.createCredential(credential, authentication , model);
        return "result";
    }

    @GetMapping("/delete-credential")
    public String deleteCredential(@RequestParam("credentialId") Integer credentialId, Model model){
        credentialService.deleteCredential(credentialId, model);
        return "result";
    }
}
