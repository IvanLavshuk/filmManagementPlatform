package com.film.management.platform.mapper;


import com.film.management.platform.dto.Request.CreateDirectorDto;
import com.film.management.platform.dto.Response.ResponseDirectorDto;
import com.film.management.platform.entity.Director;
import com.film.management.platform.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DirectorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movies",ignore = true)
    Director toEntity(CreateDirectorDto directorDto);
    @Mapping(target = "movieTitles",source = "movies",qualifiedByName = "mapMoviesToNames")
    ResponseDirectorDto toDto(Director director);
    @Named("mapMoviesToNames")
    default List<String> mapMoviesToNames(Set<Movie> movies){
        return movies.stream().map(movie -> {
            String title = movie.getTitle();
            return title;
        }).collect(Collectors.toList());
    }

}
