package com.company.app.habr.domain.repository;

import java.util.Optional;

import com.company.app.habr.domain.entity.Role;
import com.company.app.habr.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(RoleType roleType);

}