package com.example.c195projectv2.Database.Daos;

import com.example.c195projectv2.Models.Note;

import java.util.List;

public interface NoteDAOInterface {

    boolean addNote(Note note);

    Note getNoteById(int noteId);

    List<Note> getNotesByCourse(int courseId);

    int getNoteCount();

    List<Note> getNotes();

    boolean removeNote(Note note);
    boolean updateNote(Note note);
}
