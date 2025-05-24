package com.film.management.platform.service;

import com.film.management.platform.dto.Request.CreateDirectorDto;
import com.film.management.platform.dto.Response.ResponseDirectorDto;
import com.film.management.platform.entity.Director;
import com.film.management.platform.mapper.DirectorMapper;
import com.film.management.platform.repository.DirectorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DirectorService {
    private final DirectorRepository directorRepository;
    private final DirectorMapper directorMapper;

    @Transactional
    public Director create(CreateDirectorDto directorDto) {
        Optional<Director> optional = directorRepository.
                findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(directorDto.getName(), directorDto.getSurname());
        if (optional.isPresent()) {
            throw new IllegalStateException("Director with this name and surname already exists");
        }
        Director director = directorMapper.toEntity(directorDto);
        return directorRepository.save(director);
    }

    @Transactional(readOnly = true)
    public List<ResponseDirectorDto> findAll() {
        return directorRepository
                .findAll()
                .stream()
                .map(directorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseDirectorDto findById(Integer id) {
        return directorRepository
                .findById(id)
                .map(directorMapper::toDto)
                .orElseThrow(()->new EntityNotFoundException("Director with this id is not exist"));
    }

    @Transactional(readOnly = true)
    public List<ResponseDirectorDto> findAllWithSurname(String surname) {
        return directorRepository
                .findBySurnameContainingIgnoreCase(surname)
                .stream()
                .map(directorMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ResponseDirectorDto> findAllWithName(String name) {
        return directorRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(directorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseDirectorDto> findAllByBirthdateBefore(LocalDate localDate) {
        return directorRepository
                .findByBirthdateBefore(localDate)
                .stream()
                .map(directorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseDirectorDto update(CreateDirectorDto directorDto) {
        String name = directorDto.getName();
        String surname = directorDto.getSurname();
        Director director = directorRepository
                .findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(name, surname)
                .orElseThrow(()->new EntityNotFoundException("Director with this name and surname is not exist"));
        return directorMapper.toDto(directorRepository.save(director));
    }

    @Transactional
    public void deleteById(Integer id) {
        Optional<Director> optional = directorRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("director not found with id: " + id);
        }
        directorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ResponseDirectorDto> findAllWithMoviesCountry(String country) {
        return directorRepository
                .findByMovies_Country(country)
                .stream()
                .map(directorMapper::toDto)
                .collect(Collectors.toList());
    }
}
