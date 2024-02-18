package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {
    private final NoteMapper noteMapper;
    private final UserService userService;

    public void createNote(Note note, Authentication authentication, Model model){
        String username = authentication.getPrincipal().toString();

        note.setUserId(userService.getUser(username).getUserId());

        // update note if exist
        if (noteMapper.getNote(note.getNoteId()) != null) {
            noteMapper.updateNote(note);
            model.addAttribute("resultSuccess", true);
            log.info("Note updated successfully with the following values: {} ", note);
            return;
        }

        if (noteMapper.getNoteByTitle(note.getNoteTitle()) != null){
            model.addAttribute("resultError", "A note with this title already exist!");
            return;
        }

        int createdNote= noteMapper.insertNote(note);

        if (createdNote > 0 ){
            model.addAttribute("resultSuccess", true);
            log.info("Note created successfully with the following values: {} ", note);
            return;
        }
        model.addAttribute("resultError", "Something went wrong, please try again!");
        log.error("Fail in creating note with the following values: {} ", note);
    }

    public List<Note> getAllNotes(String username){
        return noteMapper.getAllNotes(userService.getUser(username).getUserId());
    }

    public void deleteNote(Integer noteId, Model model){
        noteMapper.deleteNote(noteId);
        model.addAttribute("resultSuccess",true);
    }
}
