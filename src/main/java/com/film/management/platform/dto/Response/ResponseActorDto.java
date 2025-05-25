package com.film.management.platform.dto.Response;

import com.film.management.platform.dto.Short.MovieRoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseActorDto {
    private Integer id;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String photoUrl;
    private List<MovieRoleDto> movieRoles;
}
