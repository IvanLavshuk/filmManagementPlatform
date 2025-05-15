package com.film.management.platform.mapper;

import com.film.management.platform.dto.Request.CreateGenreDto;
import com.film.management.platform.dto.Response.ResponseGenreDto;
import com.film.management.platform.entity.Genre;
import com.film.management.platform.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "movies", ignore = true)
    Genre toEntity(CreateGenreDto genreDto);

    @Mapping(target = "movieTitles", source = "movies", qualifiedByName ="mapMoviesToNames")
    ResponseGenreDto toDto(Genre genre);
    @Named("mapMoviesToNames")
    default List<String> mapMoviesToNames(Set<Movie> movies){
        return movies.stream().map(movie -> {
            String title  = movie.getTitle();
            return title;
        }).collect(Collectors.toList());
    }
}
