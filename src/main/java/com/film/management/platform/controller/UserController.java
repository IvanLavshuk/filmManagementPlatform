package com.film.management.platform.controller;

import com.film.management.platform.dto.Request.CreateUserDto;
import com.film.management.platform.dto.Response.ResponseUserDto;
import com.film.management.platform.dto.Short.LoginDto;
import com.film.management.platform.entity.User;
import com.film.management.platform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseUserDto> login(@RequestBody LoginDto login) {
        try {
            log.info("loginDto: {}", login);
            User user = userService.authenticate(login.getEmail(), login.getPassword());
            ResponseUserDto userDto = userService.findById(user.getId());
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @PostMapping("/create")
    public ResponseEntity<ResponseUserDto> create(@RequestBody CreateUserDto dto) {
        User user = userService.create(dto);
        ResponseUserDto userDto = userService.findById(user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseUserDto>> findAll() {
        List<ResponseUserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> findById(@PathVariable Integer id) {
        ResponseUserDto dto = userService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/email")
    public ResponseEntity<ResponseUserDto> findByEmail(@RequestParam String email) {
        ResponseUserDto dto = userService.findByEmail(email);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/fullName")
    public ResponseEntity<ResponseUserDto> findByNameAndSurname(@RequestParam String name,
                                                                @RequestParam String surname) {
        ResponseUserDto dto = userService.findByNameAndSurname(name, surname);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/role")
    public ResponseEntity<List<ResponseUserDto>> findByRole(@RequestParam String role) {
        List<ResponseUserDto> users = userService.findByRole(role);
        return ResponseEntity.ok(users);
    }

    @PutMapping
    public ResponseEntity<ResponseUserDto> update(@RequestBody CreateUserDto dto){
       return ResponseEntity.ok(userService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
