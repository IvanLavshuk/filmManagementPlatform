package com.film.management.platform.repository;

import com.film.management.platform.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    List<Review> findByMovie_Id(Integer movieId);
    List<Review> findByUser_Id(Integer UserId);
    List<Review> findByUser_Email(String userEmail);
    Optional<Review> findByUser_NameAndUser_SurnameAndMovie_Title(String name, String surname,String title);
    List<Review> findByMovie_Title(String title);
    Optional<Review> findByUser_EmailAndMovie_Title(String email, String title);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movie.id = :movieId")
    Double findAverageRatingByMovieId(@Param("movieId") Integer movieId);
    List<Review> findByRatingGreaterThanEqual(double rating);
    List<Review> findByRatingLessThanEqual(double rating);
    List<Review> findByTextContainingIgnoreCase(String keyword);
    boolean existsByUser_Email(String email);
}
