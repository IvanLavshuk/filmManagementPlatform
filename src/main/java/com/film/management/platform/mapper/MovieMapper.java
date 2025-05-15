package com.film.management.platform.mapper;

import com.film.management.platform.dto.Request.CreateMovieActorDto;
import com.film.management.platform.dto.Request.CreateMovieDto;
import com.film.management.platform.dto.Response.ResponseMovieDto;
import com.film.management.platform.dto.Short.ActorRoleDto;
import com.film.management.platform.entity.Director;
import com.film.management.platform.entity.Genre;
import com.film.management.platform.entity.Movie;
import com.film.management.platform.entity.MovieActor;
import com.film.management.platform.repository.ActorRepository;
import com.film.management.platform.repository.DirectorRepository;
import com.film.management.platform.repository.GenreRepository;
import com.film.management.platform.repository.MovieRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = MovieActorMapper.class)
public interface MovieMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "genres", source = "genreNames", qualifiedByName = "mapNamesToGenres")
    @Mapping(target = "directors", source = "directorFullNames", qualifiedByName = "mapNamesToDirectors")
    @Mapping(target = "movieActors", source = "actorRoles", qualifiedByName = "mapActorRoles")
    Movie toEntity(CreateMovieDto movieDto,
                   @Context MovieActorMapper movieActorMapper,
                   @Context MovieRepository movieRepository,
                   @Context ActorRepository actorRepository,
                   @Context GenreRepository genreRepository,
                   @Context DirectorRepository directorRepository);

    @Named("mapNamesToGenres")
    default Set<Genre> mapNamesToGenres(List<String> genres, @Context GenreRepository genreRepository) {
        return genres.stream().map(name ->
                genreRepository.findByNameIgnoreCase(name).orElseThrow()
        ).collect(Collectors.toSet());
    }

    @Named("mapNamesToDirectors")
    default Set<Director> mapNamesToDirectors(List<String> directorsFullNames, @Context DirectorRepository directorRepository) {
        return directorsFullNames.stream().map(fullName -> {
            String[] parts = fullName.split(" ");
            String name = parts[0];
            String surname = parts.length > 1 ? parts[1] : "";
            return directorRepository.findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(name, surname).orElseThrow();
        }).collect(Collectors.toSet());
    }

    @Named("mapActorRoles")
    default Set<MovieActor> mapActorRoles(
            List<CreateMovieActorDto> actorRoles,
            @Context MovieActorMapper movieActorMapper,
            @Context ActorRepository actorRepository,
            @Context MovieRepository movieRepository
    ) {
        return actorRoles.stream()
                .map(dto -> movieActorMapper.toEntity(dto, movieRepository, actorRepository))
                .collect(Collectors.toSet());
    }


    @Mapping(target = "genreNames", source = "genres", qualifiedByName = "mapGenresToNames")
    @Mapping(target = "directorFullNames", source = "directors", qualifiedByName = "mapDirectorsToNames")
    @Mapping(target = "actorRoles", source = "movieActors", qualifiedByName = "mapMovieActors")
    ResponseMovieDto toDto(Movie movie);

    @Named("mapGenresToNames")
    default List<String> mapGenresToNames(Set<Genre> genres) {
        return genres
                .stream()
                .map(Genre::getName)
                .collect(Collectors.toList());
    }

    @Named("mapDirectorsToNames")
    default List<String> mapDirectorsToNames(Set<Director> directors) {

        return directors.stream().map(director -> {
            String name = director.getName();
            String surname = director.getSurname();
            return name + " " + surname;
        }).collect(Collectors.toList());
    }

    @Named("mapMovieActors")
    default List<ActorRoleDto> mapMovieActors(Set<MovieActor> movieActors) {
        return movieActors.stream().map(movieActor -> {
            ActorRoleDto actorRoleDto = new ActorRoleDto();
            Integer id = movieActor.getActor().getId();
            actorRoleDto.setIdActor(id);
            String role = movieActor.getRole();
            actorRoleDto.setRole(role);
            String name = movieActor.getActor().getName();
            String surname = movieActor.getActor().getSurname();
            String actorFullName = name + " " + surname;
            actorRoleDto.setActorFullName(actorFullName);
            return actorRoleDto;
        }).collect(Collectors.toList());
    }

}
