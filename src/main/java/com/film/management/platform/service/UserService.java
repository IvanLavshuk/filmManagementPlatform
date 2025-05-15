package com.film.management.platform.service;

import com.film.management.platform.dto.Request.CreateUserDto;
import com.film.management.platform.dto.Response.ResponseUserDto;
import com.film.management.platform.entity.User;
import com.film.management.platform.mapper.ReviewMapper;
import com.film.management.platform.mapper.RoleMapper;
import com.film.management.platform.mapper.UserMapper;
import com.film.management.platform.repository.UserRepository;
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
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;
    private RoleMapper roleMapper;
    private ReviewMapper reviewMapper;

    @Transactional
    public User create(CreateUserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalStateException("User with this email already exists");
        }
        User user = userMapper.toEntity(userDto);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<ResponseUserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> userMapper.toDto(user, roleMapper, reviewMapper))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseUserDto findById(Integer id) {
        return userRepository
                .findById(id)
                .map(user -> userMapper.toDto(user, roleMapper, reviewMapper))
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public ResponseUserDto findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .map(user -> userMapper.toDto(user, roleMapper, reviewMapper))
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public ResponseUserDto findByNameAndSurname(String name, String surname) {
        return userRepository
                .findByNameAndSurname(name, surname)
                .map(user -> userMapper.toDto(user, roleMapper, reviewMapper))
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<ResponseUserDto> findByRole(String role) {
        return userRepository
                .findByRole_Name(role)
                .stream()
                .map(user -> userMapper.toDto(user, roleMapper, reviewMapper))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(Integer id) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NoSuchElementException("user not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public ResponseUserDto update(CreateUserDto userDto) {
        String email = userDto.getEmail();
        User user = userRepository.findByEmail(email).orElseThrow();
        return userMapper.toDto(userRepository.save(user), roleMapper, reviewMapper);
    }
}
