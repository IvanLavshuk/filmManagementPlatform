package com.film.management.platform.repository;

import com.film.management.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    List<User> findByRole_Name(String roleName);
    Optional<User> findByNameAndSurname(String name, String surname);
}
