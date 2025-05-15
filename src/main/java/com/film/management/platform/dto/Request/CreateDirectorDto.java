package com.film.management.platform.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDirectorDto {
    private String name;
    private String surname;
    private LocalDate birthdate;
    private String photoUrl;
}
