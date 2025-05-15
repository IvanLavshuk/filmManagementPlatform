package com.film.management.platform.mapper;

import com.film.management.platform.dto.Request.CreateActorDto;
import com.film.management.platform.dto.Response.ResponseActorDto;
import com.film.management.platform.dto.Short.MovieRoleDto;
import com.film.management.platform.entity.Actor;
import com.film.management.platform.entity.MovieActor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ActorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movieActors", ignore = true)
    Actor toEntity(CreateActorDto createActorDto);

    @Mapping(target = "movieRoles", source = "movieActors", qualifiedByName = "mapMovieActors")
    ResponseActorDto toDto(Actor actor);

    @Named("mapMovieActors")
    default List<MovieRoleDto> mapMovieActors(Set<MovieActor> movieActors) {
        return movieActors.stream().map(movieActor -> {
            MovieRoleDto movieRoleDto = new MovieRoleDto();
            String role = movieActor.getRole();
            Integer id = movieActor.getMovie().getId();
            String title = movieActor.getMovie().getTitle();
            movieRoleDto.setRole(role);
            movieRoleDto.setIdMovie(id);
            movieRoleDto.setTitle(title);
            return movieRoleDto;
        }).collect(Collectors.toList());
    }
}
