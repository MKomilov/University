package com.example.test.service;

import com.example.test.entity.Faculty;
import com.example.test.entity.Group;
import com.example.test.exception.CustomRequestException;
import com.example.test.payload.GroupDTO;
import com.example.test.repository.FacultyRepository;
import com.example.test.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final FacultyRepository facultyRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> addGroup(GroupDTO groupDTO) {
        if (groupRepository.existsByNameAndYear(groupDTO.getName(), groupDTO.getYear()))
            throw new CustomRequestException("Group already added");

        Faculty faculty = facultyRepository.findByIsDeletedFalseAndId(groupDTO.getFacultyId())
                .orElseThrow(() -> new CustomRequestException("Faculty not found with id = " + groupDTO.getFacultyId()));

        Group group = Group.builder()
                .year(groupDTO.getYear())
                .faculty(faculty)
                .build();
        group.setName(groupDTO.getName());
        group.setIsDeleted(false);
        groupRepository.save(group);
        return ResponseEntity.status(201).body("Group added");
    }

    public HttpEntity<?> getGroup(Integer id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new CustomRequestException("Group not found with id = " + id));
        return ResponseEntity.ok(group);
    }

    public HttpEntity<?> getAll() {
        return ResponseEntity.ok(groupRepository.findAll());
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> editGroup(Integer id, GroupDTO groupDTO) {
        if (groupRepository.existsByNameAndYear(groupDTO.getName(), groupDTO.getYear()))
            throw new CustomRequestException("Group already exist");

        Group group = groupRepository.findByIsDeletedFalseAndId(id)
                .orElseThrow(() -> new CustomRequestException("Group not found with id = " + id));

        Faculty faculty = facultyRepository.findByIsDeletedFalseAndId(groupDTO.getFacultyId())
                .orElseThrow(() -> new CustomRequestException("Faculty not found with id = " + groupDTO.getFacultyId()));

        group.setName(groupDTO.getName());
        group.setYear(groupDTO.getYear());
        group.setFaculty(faculty);

        groupRepository.save(group);
        return ResponseEntity.ok("Edited");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> deleteGroup(Integer id) {
        if (!groupRepository.existsById(id)) {
            throw new CustomRequestException("Group not found with id " + id);
        }

        groupRepository.deleteAndSet(id);
        return ResponseEntity.ok("Deleted");
    }
}
