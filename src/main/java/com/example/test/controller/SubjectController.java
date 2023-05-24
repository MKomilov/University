package com.example.test.controller;

import com.example.test.payload.NameDTO;
import com.example.test.service.SubjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/subject")
@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    /**
     * For subject CRUD operations
     * @param nameDTO
     * nameDTO contains info about subject name
     */

    @PostMapping
    HttpEntity<?> add(@RequestBody @Valid NameDTO nameDTO){
        return subjectService.add(nameDTO);
    }

    @GetMapping("/{id}")
    HttpEntity<?> get(@PathVariable Integer id){
        return subjectService.getSubject(id);
    }

    @GetMapping()
    HttpEntity<?> getAll(){
        return subjectService.getAll();
    }

    @PutMapping("/{id}")
    HttpEntity<?> edit(@PathVariable Integer id, @RequestBody @Valid NameDTO nameDTO){
        return subjectService.edit(id, nameDTO);
    }

    @DeleteMapping("/{id}")
    HttpEntity<?> delete(@PathVariable Integer id){
        return subjectService.delete(id);
    }
}
