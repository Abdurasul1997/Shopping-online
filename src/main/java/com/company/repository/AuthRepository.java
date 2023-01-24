package com.company.repository;

import com.company.domains.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<AuthUser, Long> {
    @Query("select p from AuthUser p where p.username = ?1")
    Optional<AuthUser> findByUsername(String username);


}
