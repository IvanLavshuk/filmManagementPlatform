package com.film.management.platform.controller;


import com.film.management.platform.dto.Request.CreateDirectorDto;
import com.film.management.platform.dto.Response.ResponseDirectorDto;
import com.film.management.platform.entity.Director;
import com.film.management.platform.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/directors")
public class DirectorController {
    private final DirectorService directorService;

    @GetMapping("/surname")
    public ResponseEntity<List<ResponseDirectorDto>> findBySurname(@RequestParam String surname) {
        List<ResponseDirectorDto> directors = directorService.findAllWithSurname(surname);
        return ResponseEntity.ok(directors);
    }

    @GetMapping("/name")
    public ResponseEntity<List<ResponseDirectorDto>> findByName(@RequestParam String name) {
        List<ResponseDirectorDto> directors = directorService.findAllWithName(name);
        return ResponseEntity.ok(directors);
    }

    @GetMapping("/birthdate-before")
    public ResponseEntity<List<ResponseDirectorDto>> findBeforeDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ResponseDirectorDto> directors = directorService.findAllByBirthdateBefore(date);
        return ResponseEntity.ok(directors);
    }

    @GetMapping("/country")
    public ResponseEntity<List<ResponseDirectorDto>> findByMovieCountry(@RequestParam String country) {
        List<ResponseDirectorDto> directors = directorService.findAllWithMoviesCountry(country);
        return ResponseEntity.ok(directors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDirectorDto> findById(@PathVariable Integer id) {
        ResponseDirectorDto dto = directorService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDirectorDto> create(@RequestBody CreateDirectorDto dto) {
        Director director = directorService.create(dto);
        ResponseDirectorDto directorDto = directorService.findById(director.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(directorDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseDirectorDto>> findAll() {
        List<ResponseDirectorDto> directors = directorService.findAll();
        return ResponseEntity.ok(directors);
    }


    @PutMapping
    public ResponseEntity<ResponseDirectorDto> update(@RequestBody CreateDirectorDto dto) {
        return ResponseEntity.ok(directorService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        directorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
