package com.film.management.platform.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseReviewDto {
    private Integer id;
    private Double rating;
    private String text;
    private LocalDate date;
    private String userFullName;
    private String title;
}

