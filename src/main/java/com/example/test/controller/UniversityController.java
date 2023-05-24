package com.example.test.controller;

import com.example.test.payload.UniversityDTO;
import com.example.test.service.UniversityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/university")
@RestController
@RequiredArgsConstructor
public class UniversityController {
    final UniversityService universityService;

    /**
     * For University CRUD operations
     * @param universityDTO
     * universityDTO contains info about university name, address and openYear
     */

    @PostMapping
    HttpEntity<?> addUniversity(@RequestBody @Valid UniversityDTO universityDTO){
        return universityService.addUniversity(universityDTO);
    }

    @GetMapping("/{id}")
    HttpEntity<?> getUniversity(@PathVariable Integer id){
        return universityService.getUniversity(id);
    }

    @GetMapping()
    HttpEntity<?> getAll(){
        return universityService.getAll();
    }

    @PutMapping("/{id}")
    HttpEntity<?> editUniversity(@PathVariable Integer id, @RequestBody @Valid UniversityDTO universityDTO){
        return universityService.editUniversity(id, universityDTO);
    }

    @DeleteMapping("/{id}")
    HttpEntity<?> deleteUniversity(@PathVariable Integer id){
        return universityService.deleteUniversity(id);
    }
}
