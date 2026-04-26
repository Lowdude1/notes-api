package com.example.notesapp.service;

import com.example.notesapp.entity.Note;
import com.example.notesapp.exception.NoteNotFoundException;
import com.example.notesapp.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class NoteService {
    private static final Logger log = LoggerFactory.getLogger(NoteService.class);

    private final NoteRepository noteRepository;

    public Note createNote(Note note) {
        log.info("Создание заметки: {}", note.getTitle());
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        log.info("Получение всех заметок");
        return noteRepository.findAll();
    }

    public Note getNoteById(Long id) {
        log.info("Получение заметки c id: {}", id);
        return noteRepository.findById(id).orElseThrow(() -> {
            log.warn("Заметка с id: {} не найдена", id);
            return new NoteNotFoundException(id);
        });
    }

    public Note updateNote(Long id, Note updateNote) {
        log.info("Обновление заметки с id: {}", id);
        Note existing = getNoteById(id);
        existing.setTitle(updateNote.getTitle());
        existing.setContent(updateNote.getContent());
        Note saved = noteRepository.save(existing);
        log.info("Обновление заметки с id: {} прошло успешно", id);
        return saved;
    }

    public void deleteNote(Long id) {
        log.info("Удаление заметки с id: {}", id);
        noteRepository.deleteById(id);
    }

    public List<Note> searchNotes(String keyword) {
        log.info("Поиск заметок по ключевому слову: {}", keyword);
        if (keyword == null || keyword.isBlank()) {
            return getAllNotes();
        }
        return noteRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
    }

    public Page<Note> getAllNotesPaginate(Pageable pageable) {
        log.info("Получение заметок с пагинацией page = {}, size = {}",
                pageable.getPageNumber(), pageable.getPageSize());
        return noteRepository.findAll(pageable);
    }
}
