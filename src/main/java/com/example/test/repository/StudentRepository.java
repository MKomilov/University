package com.example.test.repository;

import com.example.test.entity.Student;
import com.example.test.payload.GroupStudents;
import com.example.test.payload.StudentMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
@Repository
public interface StudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query(nativeQuery = true,value = "SELECT s2.name from student s " +
            "join groups g on g.id = s.group_id " +
            "join journal j on g.id = j.group_id " +
            "join journal_subjects js on j.id = js.journals_id " +
            "join subject s2 on s2.id = js.subjects_id " +
            "where cast(s.id as varchar) = ?1")
    Set<String> getStudentSubjectsById(String id);

    @Query("select new com.example.test.payload.GroupStudents(g.name, cast(count(s.name) as int)) from Faculty f join Group g on f.id = g.faculty.id " +
            "left join Student s on g.id = s.group.id " +
            "where f.id = :id group by g.name")
    Set<GroupStudents> getGroupAndStudentNumbersByFacultyId(Integer id);

    @Query("select new com.example.test.payload.StudentMark(s.name, cast(sum(m.label) as int)) from Group g " +
            "join Student s on g.id = s.group.id " +
            "join Mark m on s.id = m.student.id " +
            "where g.id = :id group by s.name order by sum(m.label) desc")
    Set<StudentMark> getStudentsAndTotalScoresByGroupId(Integer id);

    Set<Student> findStudentsByName(String name);
}
