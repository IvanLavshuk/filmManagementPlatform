package com.film.management.platform.repository;

import com.film.management.platform.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    Optional<Genre> findByNameIgnoreCase(String name);
    boolean existsByName(String name);
    void deleteByNameIgnoreCase(String name);
}
