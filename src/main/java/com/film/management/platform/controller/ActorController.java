package com.film.management.platform.controller;

import com.film.management.platform.dto.Request.CreateActorDto;
import com.film.management.platform.dto.Response.ResponseActorDto;
import com.film.management.platform.entity.Actor;
import com.film.management.platform.service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/actors")
public class ActorController {
    private final ActorService actorService;

    @GetMapping("/surname")
    public ResponseEntity<List<ResponseActorDto>> findBySurname(@RequestParam String surname) {
        List<ResponseActorDto> actors = actorService.findAllWithSurname(surname);
        return ResponseEntity.ok(actors);
    }

    @GetMapping("/name")
    public ResponseEntity<List<ResponseActorDto>> findByName(@RequestParam String name) {
        List<ResponseActorDto> actors = actorService.findAllWithName(name);
        return ResponseEntity.ok(actors);
    }

    @GetMapping("/birthdate-before")
    public ResponseEntity<List<ResponseActorDto>> findBeforeDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<ResponseActorDto> actors = actorService.findAllByBirthdateBefore(date);
        return ResponseEntity.ok(actors);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseActorDto> create(@RequestBody CreateActorDto dto) {
        Actor actor = actorService.create(dto);
        ResponseActorDto actorDto = actorService.findById(actor.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(actorDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseActorDto>> findAll() {
        List<ResponseActorDto> actors = actorService.findAll();
        return ResponseEntity.ok(actors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseActorDto> findById(@PathVariable Integer id) {
        ResponseActorDto dto = actorService.findById(id);
        return ResponseEntity.ok(dto);
    }


    @PutMapping
    public ResponseEntity<ResponseActorDto> update(@RequestBody CreateActorDto dto) {
        return ResponseEntity.ok(actorService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        actorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
