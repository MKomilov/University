package com.example.test.service;

import com.example.test.entity.Faculty;
import com.example.test.entity.University;
import com.example.test.exception.CustomRequestException;
import com.example.test.payload.FacultyDTO;
import com.example.test.repository.FacultyRepository;
import com.example.test.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;
    private final UniversityRepository universityRepository;
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> addFaculty(FacultyDTO facultyDTO) {
        if (facultyRepository.existsByName(facultyDTO.getName())) {
            throw new CustomRequestException("Faculty already exist");
        }
        University university = universityRepository.findByIsDeletedFalseAndId(facultyDTO.getUniversityId())
                .orElseThrow(() -> new CustomRequestException("University not found with id " + facultyDTO.getUniversityId()));


        Faculty faculty = new Faculty();
        faculty.setName(facultyDTO.getName());
        faculty.setUniversity(university);
        faculty.setIsDeleted(false);
        facultyRepository.save(faculty);
        return ResponseEntity.status(201).body("Faculty added");
    }

    public HttpEntity<?> getFaculty(Integer id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new CustomRequestException("Faculty not found with id = " + id));
        return ResponseEntity.ok(faculty);
    }

    public HttpEntity<?> getAll() {
        return ResponseEntity.ok(facultyRepository.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> editFaculty(Integer id, FacultyDTO facultyDTO) {
        if (facultyRepository.existsByName(facultyDTO.getName()))
            throw new CustomRequestException("Faculty already exist");

        Faculty faculty = facultyRepository.findByIsDeletedFalseAndId(id)
                .orElseThrow(() -> new CustomRequestException("Faculty not found with id = " + id));

        University university = universityRepository.findByIsDeletedFalseAndId(facultyDTO.getUniversityId())
                .orElseThrow(() -> new CustomRequestException("University not found with id " + id));

        faculty.setName(facultyDTO.getName());
        faculty.setUniversity(university);
        facultyRepository.save(faculty);
        return ResponseEntity.ok("Edited");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> deleteFaculty(Integer id) {
        if (!facultyRepository.existsById(id)) {
            throw new CustomRequestException("Faculty not found with id " + id);
        }

        facultyRepository.deleteAndSet(id);
        return ResponseEntity.ok("Deleted");
    }
}
