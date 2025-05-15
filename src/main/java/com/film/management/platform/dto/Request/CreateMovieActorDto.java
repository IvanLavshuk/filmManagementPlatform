package com.film.management.platform.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMovieActorDto {
    private String role;
    private Integer idMovie;
    private Integer idActor;
}
