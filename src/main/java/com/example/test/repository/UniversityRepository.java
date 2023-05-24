package com.example.test.repository;

import com.example.test.entity.University;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityRepository extends JpaRepository<University, Integer> {
    @Modifying
    @Transactional
    @Query("update University set isDeleted = true where id=:id")
    void deleteAndSet(Integer id);

    Optional<University> findByIsDeletedFalseAndId(Integer id);
}
