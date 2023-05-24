package com.example.test.repository;


import com.example.test.entity.Group;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    @Modifying
    @Transactional
    @Query("update Group set isDeleted = true where id=:id")
    void deleteAndSet(Integer id);

    boolean existsByNameAndYear(String name, Integer year);

    Optional<Group> findByIsDeletedFalseAndId(Integer id);
}
