package com.film.management.platform.dto.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseUserDto {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String avatarUrl;
    private ResponseRoleDto role;
    private Set<ResponseReviewDto> reviews;
}
