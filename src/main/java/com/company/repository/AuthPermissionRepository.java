package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.company.domains.AuthRole;

public interface AuthPermissionRepository extends JpaRepository<AuthRole, Long> {
}
