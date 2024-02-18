package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/add-note")
    public String createUpdateNote(@ModelAttribute("note") Note note, Authentication authentication, Model model){
        noteService.createNote(note, authentication, model);
        return "result";
    }

    @GetMapping("/delete-note")
    public String deleteNote(@RequestParam Integer noteId, Model model){
        noteService.deleteNote(noteId, model);
        return "result";
    }
}
