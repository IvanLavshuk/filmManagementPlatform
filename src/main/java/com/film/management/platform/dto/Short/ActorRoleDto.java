package com.film.management.platform.dto.Short;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorRoleDto {
    private String role;
    private Integer idActor;
    private String actorFullName;
}
