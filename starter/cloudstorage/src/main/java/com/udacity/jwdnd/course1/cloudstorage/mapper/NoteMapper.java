package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userId =#{userId}")
    List<Note> getAllNotes(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteId =#{noteId}")
    Note getNote(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteTitle = #{noteTitle}")
    Note getNoteByTitle(String title);

    @Update("UPDATE NOTES SET noteTitle =#{noteTitle}, noteDescription=#{noteDescription} WHERE noteId =#{noteId}")
    int updateNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    void deleteNote(Integer noteId);
}

