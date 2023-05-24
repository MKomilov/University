package com.example.test.controller;

import com.example.test.payload.GroupDTO;
import com.example.test.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/group")
@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    /**
     * For Group CRUD operations
     * @param groupDTO
     * groupDTO contains group name, year and facultyId
     */

    @PostMapping
    HttpEntity<?> addGroup(@RequestBody @Valid GroupDTO groupDTO){
        return groupService.addGroup(groupDTO);
    }

    @GetMapping("/{id}")
    HttpEntity<?> getGroup(@PathVariable Integer id){
        return groupService.getGroup(id);
    }

    @GetMapping()
    HttpEntity<?> getAll(){
        return groupService.getAll();
    }

    @PutMapping("/{id}")
    HttpEntity<?> editGroup(@PathVariable Integer id, @RequestBody @Valid GroupDTO groupDTO){
        return groupService.editGroup(id, groupDTO);
    }

    @DeleteMapping("/{id}")
    HttpEntity<?> deleteGroup(@PathVariable Integer id){
        return groupService.deleteGroup(id);
    }
}
