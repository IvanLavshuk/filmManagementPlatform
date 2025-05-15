package com.film.management.platform.mapper;

import com.film.management.platform.dto.Request.CreateMovieActorDto;
import com.film.management.platform.dto.Short.ActorRoleDto;
import com.film.management.platform.dto.Short.MovieRoleDto;
import com.film.management.platform.entity.Actor;
import com.film.management.platform.entity.Movie;
import com.film.management.platform.entity.MovieActor;
import com.film.management.platform.repository.ActorRepository;
import com.film.management.platform.repository.MovieRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MovieActorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movie", source = "idMovie", qualifiedByName = "mapIdToMovie")
    @Mapping(target = "actor", source = "idActor", qualifiedByName = "mapIdToActor")
    MovieActor toEntity(CreateMovieActorDto movieActorDto,
                        @Context MovieRepository movieRepository,
                        @Context ActorRepository actorRepository);

    @Named("mapIdToMovie")
    default Movie mapIdToMovie(Integer id, @Context MovieRepository movieRepository) {
        return movieRepository.findById(id).orElseThrow();
    }

    @Named("mapIdToActor")
    default Actor mapIdToActor(Integer id, @Context ActorRepository actorRepository) {
        return actorRepository.findById(id).orElseThrow();
    }

    @Mapping(target = "idActor", source = "actor", qualifiedByName = "mapActorToId")
    @Mapping(target = "actorFullName", source = "actor", qualifiedByName = "mapActorToFullName")
    ActorRoleDto toActorRoleDto(MovieActor movieActor);

    @Named("mapActorToId")
    default Integer mapActorToId(Actor actor) {
        return actor.getId();
    }

    @Named("mapActorToFullName")
    default String mapActorToFullName(Actor actor) {
        return actor.getName() + " " + actor.getSurname();
    }

    @Mapping(target = "idMovie", source = "movie", qualifiedByName = "mapMovieToId")
    @Mapping(target = "title", source = "movie", qualifiedByName = "mapMovieToTitle")
    MovieRoleDto toMovieRoleDto(MovieActor movieActor);

    @Named("mapMovieToId")
    default Integer mapMovieToId(Movie movie) {
        return movie.getId();
    }

    @Named("mapMovieToTitle")
    default String mapMovieToTitle(Movie movie) {
        return movie.getTitle();
    }

}
