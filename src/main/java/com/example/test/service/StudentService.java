package com.example.test.service;

import com.example.test.entity.Group;
import com.example.test.entity.Student;
import com.example.test.exception.CustomRequestException;
import com.example.test.payload.GroupStudents;
import com.example.test.payload.InfoDTO;
import com.example.test.payload.StudentDTO;
import com.example.test.repository.GroupRepository;
import com.example.test.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> addToGroup(StudentDTO studentDTO) {
        Student student = studentRepository.findById(studentDTO.getStudentId())
                .orElseThrow(() -> new CustomRequestException("Student not found with id " + studentDTO.getStudentId()));

        Group group = groupRepository.findById(studentDTO.getGroupId())
                .orElseThrow(() -> new CustomRequestException("Group not found with id " + studentDTO.getGroupId()));

        student.setGroup(group);
        studentRepository.save(student);
        return ResponseEntity.ok("Student is successfully added to group");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> getStudentSubjectsById(String id){
        return ResponseEntity.ok(studentRepository.getStudentSubjectsById(id));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> getGroupAndStudentNumbersByFacultyId(Integer id){
        Set<GroupStudents> groupStudents = studentRepository.getGroupAndStudentNumbersByFacultyId(id);
        int total = groupStudents.stream().mapToInt(GroupStudents::getNumberOfStudents).sum();
        return ResponseEntity.ok(new InfoDTO(groupStudents, groupStudents.size(), total));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> getFullInformationAboutStudentByName(String name){
        return ResponseEntity.ok(studentRepository.findStudentsByName(name));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public HttpEntity<?> getStudentsAndTotalScoresByGroupId(Integer id){
        return ResponseEntity.ok(studentRepository.getStudentsAndTotalScoresByGroupId(id));
    }

    public HttpEntity<?> get(UUID id){
        Student student = studentRepository.findById(id).orElseThrow(() -> new CustomRequestException("Student not found with id " + id));
        return ResponseEntity.ok(student);
    }

    public HttpEntity<?> getAllStudents(){
        return ResponseEntity.ok(studentRepository.findAll());
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
//    public HttpEntity<?> delete(UUID id) {
//        studentRepository.deleteById(id);
//        return ResponseEntity.ok("Deleted");
//    }
}
