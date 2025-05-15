package com.film.management.platform.repository;

import com.film.management.platform.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor,Integer> {
    List<Actor> findByNameContainingIgnoreCase(String name);
    List<Actor> findBySurnameContainingIgnoreCase(String surname);
    List<Actor> findByBirthdateBefore(LocalDate date);
    List<Actor> findByMovieActors_Movie_TitleAndMovieActors_Movie_LocalDate(String title, LocalDate localDate);
    Optional<Actor> findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(String name, String surname);
}
