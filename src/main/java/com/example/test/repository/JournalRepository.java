package com.example.test.repository;


import com.example.test.entity.Journal;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Integer> {
    @Modifying
    @Transactional
    @Query("update Journal set isDeleted = true where id=:id")
    void deleteAndSet(Integer id);

    boolean existsByName(String name);

    Optional<Journal> findByIsDeletedFalseAndId(Integer id);
}
