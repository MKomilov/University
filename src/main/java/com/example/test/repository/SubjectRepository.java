package com.example.test.repository;

import com.example.test.entity.Subject;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    @Modifying
    @Transactional
    @Query("update Subject set isDeleted = true where id=:id")
    void deleteAndSet(Integer id);

    boolean existsByName(String name);

    Optional<Subject> findByIsDeletedFalseAndId(Integer id);
}
