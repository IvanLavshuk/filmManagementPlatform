package com.film.management.platform.mapper;

import com.film.management.platform.dto.Request.CreateRoleDto;
import com.film.management.platform.dto.Response.ResponseRoleDto;
import com.film.management.platform.entity.Role;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role toEntity(CreateRoleDto roleDto);
    ResponseRoleDto toDto(Role role);
}
