package com.film.management.platform.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseGenreDto {
    private Integer id;
    private String name;
    private List<String> movieTitles;
}
