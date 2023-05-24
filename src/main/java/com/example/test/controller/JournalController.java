package com.example.test.controller;

import com.example.test.payload.JournalDTO;
import com.example.test.service.JournalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/journal")
@RestController
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;

    /**
     * For Journal CRUD operations
     * @param journalDTO
     * journalDTO contains journal name, groupId and set of subjectIds
     */

    @PostMapping
    HttpEntity<?> addJournal(@RequestBody @Valid JournalDTO journalDTO){
        return journalService.addJournal(journalDTO);
    }

    @GetMapping("/{id}")
    HttpEntity<?> getJournal(@PathVariable Integer id){
        return journalService.getJournal(id);
    }

    @GetMapping()
    HttpEntity<?> getAll(){
        return journalService.getAll();
    }

    @PutMapping("/{id}")
    HttpEntity<?> editJournal(@PathVariable Integer id, @RequestBody @Valid JournalDTO journalDTO){
        return journalService.editJournal(id, journalDTO);
    }

    @DeleteMapping("/{id}")
    HttpEntity<?> deleteJournal(@PathVariable Integer id){
        return journalService.deleteJournal(id);
    }
}
