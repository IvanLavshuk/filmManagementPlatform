package com.film.management.platform.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto {
    private String name;
    private String surname;
    private char[] password;
    private String avatarUrl;
    private String email;
}
