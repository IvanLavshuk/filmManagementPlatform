package com.film.management.platform.dto.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieDto {
    private String title;
    private String country;
    private LocalDate releaseDate;
    private String description;
    public String urlPoster;
    private List<CreateMovieActorDto> actorRoles;
    private List<String> genreNames;
    private List<String> directorFullNames;
}
