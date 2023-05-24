package com.example.test.controller;

import com.example.test.payload.FacultyDTO;
import com.example.test.service.FacultyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/faculty")
@RestController
@RequiredArgsConstructor
public class FacultyController {
    final FacultyService facultyService;

    /**
     * For Faculty CRUD operations
     * @param facultyDTO
     * facultyDTO contains faculty name and universityId
     */

    @PostMapping
    HttpEntity<?> addFaculty(@RequestBody @Valid FacultyDTO facultyDTO){
        return facultyService.addFaculty(facultyDTO);
    }

    @GetMapping("/{id}")
    HttpEntity<?> getFaculty(@PathVariable Integer id){
        return facultyService.getFaculty(id);
    }

    @GetMapping()
    HttpEntity<?> getAll(){
        return facultyService.getAll();
    }

    @PutMapping("/{id}")
    HttpEntity<?> editFaculty(@PathVariable Integer id, @RequestBody @Valid FacultyDTO facultyDTO){
        return facultyService.editFaculty(id, facultyDTO);
    }

    @DeleteMapping("/{id}")
    HttpEntity<?> deleteUniversity(@PathVariable Integer id){
        return facultyService.deleteFaculty(id);
    }
}
