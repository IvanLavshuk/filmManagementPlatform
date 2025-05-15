package com.film.management.platform.repository;

import com.film.management.platform.entity.Movie;
import com.film.management.platform.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface MovieRepository extends JpaRepository<Movie,Integer> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    List<Movie> findByTitleStartingWith(String prefix);
    List<Movie> findByCountryIgnoreCase(String country);
    List<Movie> findByGenres_NameIgnoreCase(String name);
    List<Movie> findByDirectors_NameIgnoreCaseAndDirectors_SurnameIgnoreCase(String name, String surname);
    List<Movie> findByReleaseDateBetween(LocalDate start, LocalDate end);
    @Query("""
        SELECT m FROM Movie m
        LEFT JOIN m.reviews r
        GROUP BY m
        ORDER BY AVG(r.rating) DESC
    """)
    List<Movie> findAllOrderByAverageRatingDesc();
    List<Movie> findAllByOrderByReleaseDateDesc();
    List<Movie> findAllByOrderByReleaseDateAsc();
    Optional<Movie> findByTitleAndReleaseDate(String title, LocalDate releaseDate);
    Optional<Movie> findByTitle(String title);
    void deleteByTitleAndReleaseDate(String title, LocalDate releaseDate);
}
