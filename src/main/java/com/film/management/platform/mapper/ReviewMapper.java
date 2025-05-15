package com.film.management.platform.mapper;

import com.film.management.platform.dto.Request.CreateReviewDto;
import com.film.management.platform.dto.Response.ResponseReviewDto;
import com.film.management.platform.entity.Movie;
import com.film.management.platform.entity.Review;
import com.film.management.platform.entity.User;
import com.film.management.platform.repository.MovieRepository;
import com.film.management.platform.repository.UserRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userFullName", qualifiedByName = "mapNameToUser")
    @Mapping(target = "movie", source = "title", qualifiedByName = "mapNameToMovie")
    Review toEntity(CreateReviewDto reviewDto,
                    @Context UserRepository userRepository,
                    @Context MovieRepository movieRepository);

    @Named("mapNameToUser")
    default User mapNameToUser(String fullName, @Context UserRepository userRepository) {
        String[] parts = fullName.split(" ");
        String name = parts[0];
        String surname = parts.length > 1 ? parts[1] : "";

        User user = userRepository.findByNameAndSurname(name, surname).orElseThrow();
        return user;
    }

    @Named("mapNameToMovie")
    default Movie mapNameToMovie(String title, @Context MovieRepository movieRepository) {
        Movie movie = movieRepository.findByTitle(title).orElseThrow();
        return movie;
    }

    @Mapping(target = "userFullName", source = "user", qualifiedByName = "mapUserToName")
    @Mapping(target = "title", source = "movie", qualifiedByName = "mapMovieToName")
    ResponseReviewDto toDto(Review review);

    @Named("mapUserToName")
    default String mapUserToName(User user) {
        String name = user.getName();
        String surname = user.getSurname();
        String fullName = name + " " + surname;
        return fullName;
    }

    @Named("mapMovieToName")
    default String mapMovieToName(Movie movie) {
        String title = movie.getTitle();
        return title;
    }
}
