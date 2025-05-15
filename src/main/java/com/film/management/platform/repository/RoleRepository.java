package com.film.management.platform.repository;

import com.film.management.platform.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByNameIgnoreCase(String name);
    boolean existsByName(String name);
    void deleteByNameIgnoreCase(String name);
}
