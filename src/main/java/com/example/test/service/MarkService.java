package com.example.test.service;

import com.example.test.entity.*;
import com.example.test.exception.CustomRequestException;
import com.example.test.payload.JournalDTO;
import com.example.test.payload.MarkDTO;
import com.example.test.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MarkService {
    private final JournalRepository journalRepository;
    private final StudentRepository studentRepository;
    private final MarkRepository markRepository;
    private final SubjectRepository subjectRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> add(MarkDTO markDTO) {
        if (markRepository.existsByStudentIdAndJournalIdAndSubjectId(markDTO.getStudentId(), markDTO.getJournalId(), markDTO.getSubjectId()))
            throw new CustomRequestException("Student was marked before for this subject");

        Mark mark = parser(new Mark(), markDTO);
        markRepository.save(mark);
        return ResponseEntity.status(201).body("Mark added");
    }

    private Mark parser(Mark mark, MarkDTO markDTO){
        Journal journal = journalRepository.findByIsDeletedFalseAndId(markDTO.getJournalId())
                .orElseThrow(() -> new CustomRequestException("Journal not found with id " + markDTO.getJournalId()));

        Student student = studentRepository.findById(markDTO.getStudentId())
                .orElseThrow(() -> new CustomRequestException("Student not found with id " + markDTO.getStudentId()));

        Subject subject = subjectRepository.findByIsDeletedFalseAndId(markDTO.getSubjectId())
                .orElseThrow(() -> new CustomRequestException("Subject not found with id " + markDTO.getSubjectId()));

        mark.setLabel(markDTO.getLabel());
        mark.setJournal(journal);
        mark.setSubject(subject);
        mark.setStudent(student);
        return mark;
    }

    public HttpEntity<?> get(Integer id) {
        return ResponseEntity.ok(getMarkById(id));
    }

    private Mark getMarkById(Integer id){
        return markRepository.findById(id)
                .orElseThrow(() -> new CustomRequestException("Mark not found with id = " + id));
    }

    public HttpEntity<?> getAll() {
        return ResponseEntity.ok(markRepository.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> edit(Integer id, MarkDTO markDTO) {
        Mark mark = getMarkById(id);
        parser(mark, markDTO);

        markRepository.save(mark);
        return ResponseEntity.ok("Edited");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> delete(Integer id) {
        markRepository.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}
