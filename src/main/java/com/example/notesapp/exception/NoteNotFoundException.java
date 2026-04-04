package com.example.notesapp.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long id) {
        super("Не нашлось заметки с таким id: " + id);
    }
}
