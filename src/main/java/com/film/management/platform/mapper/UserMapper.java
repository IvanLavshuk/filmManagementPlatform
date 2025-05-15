package com.film.management.platform.mapper;

import com.film.management.platform.dto.Request.CreateUserDto;
import com.film.management.platform.dto.Response.ResponseReviewDto;
import com.film.management.platform.dto.Response.ResponseRoleDto;
import com.film.management.platform.dto.Response.ResponseUserDto;
import com.film.management.platform.entity.Review;
import com.film.management.platform.entity.Role;
import com.film.management.platform.entity.User;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, ReviewMapper.class})
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(CreateUserDto userDto);

    @Mapping(target = "role", source = "role", qualifiedByName = "mapRole")
    @Mapping(target = "reviews", source = "reviews", qualifiedByName = "mapReviews")
    ResponseUserDto toDto(User user, @Context RoleMapper roleMapper, @Context ReviewMapper reviewMapper);

    @Named("mapRole")
    default ResponseRoleDto mapRole(Role role, @Context RoleMapper roleMapper) {
        ResponseRoleDto roleDto = roleMapper.toDto(role);
        return roleDto;
    }

    @Named("mapReviews")
    default Set<ResponseReviewDto> mapReviews(Set<Review> reviews, @Context ReviewMapper reviewMapper) {
        return reviews.stream().map(review -> reviewMapper.toDto(review)).collect(Collectors.toSet());
    }
}
