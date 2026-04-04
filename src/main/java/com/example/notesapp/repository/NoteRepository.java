package com.example.notesapp.repository;

import com.example.notesapp.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String titleKeyWord, String contentKeyWord);
}
