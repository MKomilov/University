package com.example.test.service;


import com.example.test.entity.University;
import com.example.test.exception.CustomRequestException;
import com.example.test.payload.UniversityDTO;
import com.example.test.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniversityService {
    private final UniversityRepository universityRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> addUniversity(UniversityDTO universityDTO) {
        University university = mapper(new University(), universityDTO);
        university.setIsDeleted(false);
        universityRepository.save(university);
        return ResponseEntity.status(201).body("University added");
    }

    private University mapper(University university, UniversityDTO universityDTO){
        university.setName(universityDTO.getName());
        university.setAddress(universityDTO.getAddress());
        university.setOpenYear(universityDTO.getOpenYear());
        return university;
    }

    public HttpEntity<?> getUniversity(Integer id) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new CustomRequestException("University not found with id = " + id));
        return ResponseEntity.ok(university);
    }

    public HttpEntity<?> getAll() {
        return ResponseEntity.ok(universityRepository.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> editUniversity(Integer id, UniversityDTO universityDTO) {
        University university = universityRepository.findById(id)
                .orElseThrow(() -> new CustomRequestException("University not found with id = " + id));
        universityRepository.save(mapper(university, universityDTO));
        return ResponseEntity.ok("Edited");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> deleteUniversity(Integer id) {
        if (!universityRepository.existsById(id)){
            throw new CustomRequestException("University not found with id " + id);
        }
        universityRepository.deleteAndSet(id);
        return ResponseEntity.ok("Deleted");
    }
}
