package com.film.management.platform.controller;


import com.film.management.platform.dto.Request.CreateMovieActorDto;
import com.film.management.platform.dto.Short.ActorRoleDto;
import com.film.management.platform.dto.Short.MovieRoleDto;
import com.film.management.platform.service.MovieActorService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies-actors")
public class MovieActorController {
    private final MovieActorService movieActorService;

    @GetMapping("/actor-id/{id}")
    public ResponseEntity<List<MovieRoleDto>> findByActorId(@PathVariable Integer id) {
        List<MovieRoleDto> dto = movieActorService.findByActorId(id);
        return ResponseEntity.ok(dto);
    }
    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CreateMovieActorDto dto) {
        movieActorService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        movieActorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/movie-id/{id}")
    public ResponseEntity<List<ActorRoleDto>> findByMovieId(@PathVariable Integer id) {
        List<ActorRoleDto> dto = movieActorService.findByMovieId(id);
        return ResponseEntity.ok(dto);
    }



    @GetMapping("/title")
    public ResponseEntity<List<ActorRoleDto>> findByTitle(@RequestParam String title){
        List<ActorRoleDto> movies = movieActorService.findByTitle(title);
        return ResponseEntity.ok(movies);
    }



}
