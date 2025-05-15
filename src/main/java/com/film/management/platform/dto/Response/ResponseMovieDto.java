package com.film.management.platform.dto.Response;

import com.film.management.platform.dto.Short.ActorRoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMovieDto {
    private Integer id;
    private String title;
    private String country;
    private LocalDate releaseDate;
    private String description;
    public String urlPoster;
    private List<String> genreNames;
    private List<String> directorFullNames;
    private List<ActorRoleDto> actorRoles;
}
