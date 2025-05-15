package com.film.management.platform.service;

import com.film.management.platform.dto.Request.CreateGenreDto;
import com.film.management.platform.dto.Request.CreateRoleDto;
import com.film.management.platform.dto.Response.ResponseGenreDto;
import com.film.management.platform.dto.Response.ResponseRoleDto;
import com.film.management.platform.entity.Genre;
import com.film.management.platform.entity.Role;
import com.film.management.platform.mapper.RoleMapper;
import com.film.management.platform.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@AllArgsConstructor
public class RoleService {
    private RoleRepository roleRepository;
    private RoleMapper roleMapper;
    @Transactional
    public Role create(CreateRoleDto roleDto){
        String name = roleDto.getName();
        Optional<Role> optional = roleRepository.findByNameIgnoreCase(name);
        if (optional.isPresent()) {
            throw new IllegalStateException("Role with this name already exists");
        }
        Role role = roleMapper.toEntity(roleDto);
        Role saved = roleRepository.save(role);
        return saved;
    }
    @Transactional(readOnly = true)
    public List<ResponseRoleDto> findAll() {
        return roleRepository
                .findAll()
                .stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseRoleDto findByName(String name) {
        if (!roleRepository.existsByName(name)) {
            throw new NoSuchElementException("Role not found with name: " + name);
        }
        return roleRepository
                .findByNameIgnoreCase(name)
                .map(roleMapper::toDto)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public ResponseRoleDto findById(Integer id) {
        return roleRepository
                .findById(id)
                .map(roleMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public ResponseRoleDto update(CreateRoleDto roleDto) {
        String name = roleDto.getName();
        Role role = roleRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new NoSuchElementException("Role not found with name: " + name));

        if (!role.getName().equalsIgnoreCase(roleDto.getName())
                && roleRepository.existsByName(roleDto.getName())) {
            throw new IllegalStateException("Another role with this name already exists");
        }

        role = roleMapper.toEntity(roleDto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Transactional(readOnly = true)
    public void deleteByName(String name) {
        if (!roleRepository.existsByName(name)) {
            throw new NoSuchElementException("role not found with name: " + name);
        }
        roleRepository.deleteByNameIgnoreCase(name);
    }
}
