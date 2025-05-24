package com.film.management.platform.controller;


import com.film.management.platform.dto.Request.CreateGenreDto;
import com.film.management.platform.dto.Response.ResponseGenreDto;
import com.film.management.platform.entity.Genre;
import com.film.management.platform.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genres")
public class GenreController {
    private final GenreService genreService;
    @PostMapping("/create")
    public ResponseEntity<ResponseGenreDto> create(@RequestBody CreateGenreDto dto) {
        Genre genre = genreService.create(dto);
        ResponseGenreDto genreDto = genreService.findById(genre.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(genreDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseGenreDto>> findAll() {
        List<ResponseGenreDto> genres = genreService.findAll();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGenreDto> findById(@PathVariable Integer id) {
        ResponseGenreDto dto = genreService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping
    public ResponseEntity<ResponseGenreDto> update(@RequestBody CreateGenreDto dto){
        return ResponseEntity.ok(genreService.update(dto));
    }
    @DeleteMapping("/delete-by-name")
    public ResponseEntity<Void> delete(@RequestParam String name){
        genreService.deleteByName(name);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        genreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/name")
    public ResponseEntity<ResponseGenreDto> findByName(@RequestParam String name){
        ResponseGenreDto genres = genreService.findByName(name);
        return ResponseEntity.ok(genres);
    }
}
