package com.example.test.repository;


import com.example.test.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface MarkRepository extends JpaRepository<Mark, Integer> {
    boolean existsByStudentIdAndJournalIdAndSubjectId(UUID student_id, Integer journal_id, Integer subject_id);
}
