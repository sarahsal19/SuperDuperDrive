package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;

public interface NoteService {

    void createNote(Note note, Authentication authentication, Model model);
    List<Note> getAllNotes(String username);
    void deleteNote(Integer noteId, Model model);
}
