package com.example.test.repository;


import com.example.test.entity.Authority;
import com.example.test.entity.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Authority, Integer> {
    Authority findAuthorityByRole(RoleEnum role);
}
