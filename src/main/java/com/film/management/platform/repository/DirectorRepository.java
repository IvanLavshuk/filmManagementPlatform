package com.film.management.platform.repository;

import com.film.management.platform.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DirectorRepository extends JpaRepository<Director,Integer> {
    List<Director> findBySurnameContainingIgnoreCase(String surname);
    List<Director> findByNameContainingIgnoreCase(String surname);
    Optional<Director> findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(String name, String surname);
    List<Director> findByBirthdateBefore(LocalDate date);
    List<Director> findByMovies_Country(String country);
}
