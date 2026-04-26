package com.example.notesapp.Service;

import com.example.notesapp.entity.Note;
import com.example.notesapp.exception.NoteNotFoundException;
import com.example.notesapp.repository.NoteRepository;
import com.example.notesapp.service.NoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void shouldCreateNote() {
        Note note = new Note();

        note.setTitle("Java");
        note.setContent("Spring Boot");

        when(noteRepository.save(note)).thenReturn(note);

        Note result = noteService.createNote(note);

        verify(noteRepository).save(note);

        assertEquals("Java", result.getTitle());
        assertEquals("Spring Boot", result.getContent());
    }

    @Test
    void shouldReturnNoteById() {
        Note note = new Note();
        Long id = 1L;
        note.setId(id);

        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        Note result = noteService.getNoteById(id);

        assertNotNull(result);
        assertEquals(note.getId(), result.getId());
    }

    @Test
    void shouldThrowExceptionWhenNoteNotFound() {
        Note note = new Note();
        Long id = 1L;
        note.setId(id);

        when(noteRepository.findById(id)).thenReturn(Optional.empty());
        NoteNotFoundException noteNotFoundException = assertThrows(NoteNotFoundException.class,
                () -> noteService.getNoteById(id));

        assertEquals("Не нашлось заметки с таким id: " + id, noteNotFoundException.getMessage());
    }

    @Test
    void shouldDeleteNoteById() {
        Long id = 1L;

        noteService.deleteNote(id);

        verify(noteRepository).deleteById(id);
    }

    @Test
    void shouldReturnAllNotes() {
        Note note1 = new Note();
        note1.setId(1L);
        note1.setTitle("Java");

        Note note2 = new Note();
        note2.setId(2L);
        note2.setTitle("Core");
        List<Note> list = List.of(note1, note2);

        when(noteRepository.findAll()).thenReturn(list);
        List<Note> result = noteService.getAllNotes();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getTitle());
        assertEquals("Core", result.get(1).getTitle());
    }

    @Test
    void shouldUpdateNoteById() {
        Long id = 1L;
        Note existing = new Note();
        existing.setId(id);
        existing.setTitle("Старая заметка");
        existing.setContent("Старый контент");

        Note updatedNote = new Note();
        updatedNote.setTitle("Новая заметка");
        updatedNote.setContent("Новый контент");

        when(noteRepository.findById(id)).thenReturn(Optional.of(existing));
        when(noteRepository.save(any(Note.class))).thenAnswer(inv -> inv.getArgument(0));

        Note result = noteService.updateNote(id, updatedNote);

        assertEquals("Новая заметка", result.getTitle());
        assertEquals("Новый контент", result.getContent());
        assertEquals(id, result.getId());
    }

    @Test
    void shouldThrowThrowExceptionWhenUpdatingNoteFound() {
        Long id = 1L;
        Note updateNote = new Note();
        updateNote.setTitle("Новая заметка");
        updateNote.setContent("Новый контент");

        when(noteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoteNotFoundException.class, () -> noteService.updateNote(id, updateNote));
    }

    @Test
    void shouldReturnAllNotesWhenKeywordIsBlank() {
        List<Note> list = List.of(new Note(), new Note());


    }
}
