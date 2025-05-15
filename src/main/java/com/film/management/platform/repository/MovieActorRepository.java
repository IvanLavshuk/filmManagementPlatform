package com.film.management.platform.repository;

import com.film.management.platform.entity.MovieActor;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface MovieActorRepository extends JpaRepository<MovieActor,Integer> {
    List<MovieActor> findByMovie_Id(Integer movieId);
    List<MovieActor> findByMovie_Title(String movie);
    List<MovieActor> findByActor_Id(Integer actorId);
    List<MovieActor> findByActor_Role(String role);
    Optional<MovieActor> findByMovie_IdAndActor_Id(Integer movieId, Integer actorId);
    List<MovieActor> findByActor_IdOrderByMovie_ReleaseDateDesc(Integer actorId);

}
