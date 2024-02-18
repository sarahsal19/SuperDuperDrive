package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.impl.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class HomePageController {
    private final CredentialsService credentialService;
    private final FileService fileService;
    private final NoteService noteService;
    private final EncryptionService encryptionService;

    @GetMapping("/home")
    public String homeView(Model model, Authentication authentication) {
        model.addAttribute("files", fileService.getAllFiles(authentication.getPrincipal().toString()));
        model.addAttribute("notes", noteService.getAllNotes(authentication.getPrincipal().toString()));
        model.addAttribute("credentials", credentialService.getAllCredentials(authentication.getPrincipal().toString()));
        model.addAttribute("encryptionService", encryptionService);
        return "home";
    }
}