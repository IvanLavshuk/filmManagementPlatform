package com.film.management.platform.controller;

import com.film.management.platform.dto.Request.CreateRoleDto;
import com.film.management.platform.dto.Response.ResponseRoleDto;
import com.film.management.platform.entity.Role;
import com.film.management.platform.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<ResponseRoleDto> create(@RequestBody CreateRoleDto dto) {
        Role role = roleService.create(dto);
        ResponseRoleDto roleDto = roleService.findById(role.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(roleDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseRoleDto>> findAll() {
        List<ResponseRoleDto> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseRoleDto> findById(@PathVariable Integer id) {
        ResponseRoleDto dto = roleService.findById(id);
        return ResponseEntity.ok(dto);
    }
    @PutMapping
    public ResponseEntity<ResponseRoleDto> update(@RequestBody CreateRoleDto dto) {
        return ResponseEntity.ok(roleService.update(dto));
    }

    @DeleteMapping("/delete-by-name")
    public ResponseEntity<Void> delete(@RequestParam String name) {
        roleService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name")
    public ResponseEntity<ResponseRoleDto> findByName(@RequestParam String name) {
        ResponseRoleDto role = roleService.findByName(name);
        return ResponseEntity.ok(role);
    }
}