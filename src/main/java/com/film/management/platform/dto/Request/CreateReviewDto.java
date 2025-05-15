package com.film.management.platform.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewDto {
    private Double rating;
    private String text;
    private LocalDate date;
    private String userFullName;
    private String title;
}
