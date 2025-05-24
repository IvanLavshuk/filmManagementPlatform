package com.film.management.platform.service;

import com.film.management.platform.dto.Request.CreateGenreDto;
import com.film.management.platform.dto.Response.ResponseGenreDto;
import com.film.management.platform.entity.Genre;
import com.film.management.platform.mapper.GenreMapper;
import com.film.management.platform.repository.GenreRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@RequiredArgsConstructor
public class GenreService {
    private GenreRepository genreRepository;
    private GenreMapper genreMapper;

    @Transactional
    public Genre create(CreateGenreDto genreDto) {
        String name = genreDto.getName();
        Optional<Genre> optional = genreRepository.findByNameIgnoreCase(name);
        if (optional.isPresent()) {
            throw new IllegalStateException("Genre with this name already exists");
        }
        Genre genre = genreMapper.toEntity(genreDto);
        Genre saved = genreRepository.save(genre);
        return saved;
    }

    @Transactional(readOnly = true)
    public List<ResponseGenreDto> findAll() {
        return genreRepository
                .findAll()
                .stream()
                .map(genreMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseGenreDto findByName(String name) {
        if (!genreRepository.existsByName(name)) {
            throw new EntityNotFoundException("genre not found with name: " + name);
        }
        return genreRepository
                .findByNameIgnoreCase(name)
                .map(genreMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("genre not found with name: " + name));
    }

    @Transactional(readOnly = true)
    public ResponseGenreDto findById(Integer id) {
        return genreRepository
                .findById(id)
                .map(genreMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("genre not found with id: " + id));
    }

    @Transactional
    public ResponseGenreDto update(CreateGenreDto genreDto) {
        String name = genreDto.getName();
        Genre genre = genreRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("genre not found with name: " + name));

        if (!genre.getName().equalsIgnoreCase(genreDto.getName())
                && genreRepository.existsByName(genreDto.getName())) {
            throw new IllegalStateException("Another genre with this name already exists");
        }

        genre = genreMapper.toEntity(genreDto);
        return genreMapper.toDto(genreRepository.save(genre));
    }

    @Transactional(readOnly = true)
    public void deleteByName(String name) {
        if (!genreRepository.existsByName(name)) {
            throw new NoSuchElementException("Genre not found with name: " + name);
        }
        genreRepository.deleteByNameIgnoreCase(name);
    }

    @Transactional
    public void deleteById(Integer id) {
        Optional<Genre> optional = genreRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("genre not found with id: " + id);
        }
        genreRepository.deleteById(id);
    }
}
