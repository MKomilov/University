package com.example.test.controller;

import com.example.test.payload.MarkDTO;
import com.example.test.service.MarkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/mark")
@RestController
@RequiredArgsConstructor
public class MarkController {
    private final MarkService markService;

    /**
     * For Mark CRUD operations
     * @param markDTO
     * markDTO contains info about mark(label) must be between 1 and 100, studentId, journalId and subjectId
     */

    @PostMapping
    HttpEntity<?> add(@RequestBody @Valid MarkDTO markDTO){
        return markService.add(markDTO);
    }

    @GetMapping("/{id}")
    HttpEntity<?> get(@PathVariable Integer id){
        return markService.get(id);
    }

    @GetMapping()
    HttpEntity<?> getAll(){
        return markService.getAll();
    }

    @PutMapping("/{id}")
    HttpEntity<?> edit(@PathVariable Integer id, @RequestBody @Valid MarkDTO markDTO){
        return markService.edit(id, markDTO);
    }

    @DeleteMapping("/{id}")
    HttpEntity<?> deleteJournal(@PathVariable Integer id){
        return markService.delete(id);
    }
}
