package com.example.test.service;

import com.example.test.entity.Subject;
import com.example.test.exception.CustomRequestException;
import com.example.test.payload.NameDTO;
import com.example.test.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> add(NameDTO nameDTO) {
        if (subjectRepository.existsByName(nameDTO.getName()))
            throw new CustomRequestException(nameDTO.getName() + " is already exist");
        Subject subject = new Subject();
        subject.setName(nameDTO.getName());
        subject.setIsDeleted(false);
        subjectRepository.save(subject);
        return ResponseEntity.status(201).body("Subject added");
    }

    public HttpEntity<?> getSubject(Integer id) {
        return ResponseEntity.ok(getSubjectById(id));
    }

    private Subject getSubjectById(Integer id){
        return subjectRepository.findById(id)
                .orElseThrow(() -> new CustomRequestException("Subject not found with id = " + id));
    }

    public HttpEntity<?> getAll() {
        return ResponseEntity.ok(subjectRepository.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> edit(Integer id, NameDTO nameDTO) {
        if (subjectRepository.existsByName(nameDTO.getName()))
            throw new CustomRequestException(nameDTO.getName() + " is already exist");

        Subject subject = getSubjectById(id);
        subject.setName(nameDTO.getName());
        subjectRepository.save(subject);
        return ResponseEntity.ok("Edited");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> delete(Integer id) {
        if (!subjectRepository.existsById(id))
            throw new CustomRequestException("Subject not found with id = " + id);

        subjectRepository.deleteAndSet(id);
        return ResponseEntity.ok("Deleted");
    }
}
