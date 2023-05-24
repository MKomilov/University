package com.example.test.service;

import com.example.test.entity.Group;
import com.example.test.entity.Journal;
import com.example.test.entity.Subject;
import com.example.test.exception.CustomRequestException;
import com.example.test.payload.JournalDTO;
import com.example.test.repository.GroupRepository;
import com.example.test.repository.JournalRepository;
import com.example.test.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class JournalService {
    private final JournalRepository journalRepository;
    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> addJournal(JournalDTO journalDTO) {
        if (journalRepository.existsByName(journalDTO.getName()))
            throw new CustomRequestException(journalDTO.getName() + " is already exist");

        Journal journal = mapper(new Journal(), journalDTO);
        journal.setIsDeleted(false);
        journalRepository.save(journal);
        return ResponseEntity.status(201).body("Journal added");
    }

    public HttpEntity<?> getJournal(Integer id) {
        Journal journal = getJournalById(id);
        return ResponseEntity.ok(journal);
    }

    private Journal getJournalById(Integer id){
        return journalRepository.findById(id)
                .orElseThrow(() -> new CustomRequestException("Journal not found with id = " + id));
    }

    private Journal mapper(Journal journal, JournalDTO journalDTO){
        Group group = groupRepository.findByIsDeletedFalseAndId(journalDTO.getGroupId())
                .orElseThrow(() -> new CustomRequestException("Group not found with id " + journalDTO.getGroupId()));

        Set<Subject> subjects = new HashSet<>();
        journalDTO.getSubjectIds().stream()
                .map(subjectRepository::findById)
                .forEach(optionalSubject -> optionalSubject.ifPresent(subjects::add));
        journal.setGroup(group);
        journal.setSubjects(subjects);
        journal.setName(journalDTO.getName());
        return journal;
    }

    public HttpEntity<?> getAll() {
        return ResponseEntity.ok(journalRepository.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> editJournal(Integer id, JournalDTO journalDTO) {
        Journal journal = getJournalById(id);
        mapper(journal, journalDTO);

        journalRepository.save(journal);
        return ResponseEntity.ok("Edited");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> deleteJournal(Integer id) {
        if (!journalRepository.existsById(id))
            throw new CustomRequestException("Journal not found with id " + id);

        journalRepository.deleteAndSet(id);
        return ResponseEntity.ok("Deleted");
    }
}
