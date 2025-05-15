package com.film.management.platform.dto.Short;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRoleDto {
    private String role;
    private Integer idMovie;
    private String title;
}
