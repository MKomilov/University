package com.example.test.controller;

import com.example.test.payload.NameDTO;
import com.example.test.payload.StudentDTO;
import com.example.test.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/student")
@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    /**
     * Only admin can add other students to their groups
     * @param studentDTO
     * studentDTO contains info about studentId and groupId
     */

    @PostMapping
    public HttpEntity<?> addStudentToGroup(@RequestBody @Valid StudentDTO studentDTO){
        return studentService.addToGroup(studentDTO);
    }

    /**
     * Only admin can get information about other students' learning subjects by their id
     * @param id
     * id is student id
     */
    @GetMapping("/getSubjectsById/{id}")
    public HttpEntity<?> getStudentSubjectsById(@PathVariable String id){
        return studentService.getStudentSubjectsById(id);
    }

    /**
     * For getting full information about number of groups and its students by faculty id
     * @param id
     * id is faculty id
     */

    @GetMapping("/getGroupAndStudentNumbersByFacultyId/{id}")
    public HttpEntity<?> getGroupAndStudentNumbersByFacultyId(@PathVariable Integer id){
        return studentService.getGroupAndStudentNumbersByFacultyId(id);
    }

    /**
     * For getting full information about student by his/her name
     * @param nameDTO
     * nameDTO contains info about student name
     * @return
     * return info about set of students in case there are multiple students with same name
     */

    @GetMapping("/getFullInformationAboutStudentByName")
    public HttpEntity<?> getFullInformationAboutStudentByName(@RequestBody @Valid NameDTO nameDTO){
        return studentService.getFullInformationAboutStudentByName(nameDTO.getName());
    }

    /**
     * For getting full information about students' total scores by group id
     * @param id
     * id is group id
     * @return
     * return student name and his/her total scores from subjects in descending order
     */
    @GetMapping("/getStudentsAndTotalScoresByGroupId/{id}")
    public HttpEntity<?> getStudentsAndTotalScoresByGroupId(@PathVariable Integer id){
        return studentService.getStudentsAndTotalScoresByGroupId(id);
    }

    /**
     * For getting info about particular student by its id
     * @param id
     * id is student id
     */

    @GetMapping("/{id}")
    public HttpEntity<?> get(@PathVariable UUID id){
        return studentService.get(id);
    }

    /**
     * @return
     * return all students from database
     */

    @GetMapping
    public HttpEntity<?> getAllStudents(){
        return studentService.getAllStudents();
    }

//    @DeleteMapping("/{id}")
//    public HttpEntity<?> delete(@PathVariable UUID id){
//        return studentService.delete(id);
//    }
}
