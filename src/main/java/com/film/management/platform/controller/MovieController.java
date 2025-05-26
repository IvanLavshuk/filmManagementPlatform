package com.film.management.platform.controller;

import com.film.management.platform.dto.Request.CreateMovieDto;
import com.film.management.platform.dto.Response.ResponseMovieDto;

import com.film.management.platform.entity.Movie;
import com.film.management.platform.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    @GetMapping("/country")
    public ResponseEntity<List<ResponseMovieDto>> findByCountry(@RequestParam String country){
        List<ResponseMovieDto> movies = movieService.findByCountry(country);
        return ResponseEntity.ok(movies);
    }
    @GetMapping("/order-rating-desc")
    public ResponseEntity<List<ResponseMovieDto>> findByOrderRatingDesc() {
        List<ResponseMovieDto> movies = movieService.findAllOrderByAverageRatingDesc();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/director")
    public ResponseEntity<List<ResponseMovieDto>> findByDirector(@RequestParam String name, @RequestParam String surname){
        List<ResponseMovieDto> movies = movieService.findByDirector(name,surname);
        return ResponseEntity.ok(movies);
    }


    @GetMapping("/order-date-desc")
    public ResponseEntity<List<ResponseMovieDto>> findByOrderDateDesc() {
        List<ResponseMovieDto> movies = movieService.findAllByOrderByDateDesc();
        return ResponseEntity.ok(movies);
    }
    @GetMapping("/order-date-asc")
    public ResponseEntity<List<ResponseMovieDto>> findByOrderDateAsc() {
        List<ResponseMovieDto> movies = movieService.findAllByOrderByDateAsc();
        return ResponseEntity.ok(movies);
    }


    @GetMapping("/title")
    public ResponseEntity<List<ResponseMovieDto>> findByTitle(@RequestParam String title){
        List<ResponseMovieDto> movies = movieService.findByTitle(title);
        return ResponseEntity.ok(movies);
    }
    @GetMapping("/genre")
    public ResponseEntity<List<ResponseMovieDto>> findByGenre(@RequestParam String genre){
        List<ResponseMovieDto> movies = movieService.findByGenre(genre);
        return ResponseEntity.ok(movies);
    }
    @GetMapping("/director/{id}")
    public ResponseEntity<List<ResponseMovieDto>> findByDirectorId(@PathVariable Integer id){
        List<ResponseMovieDto> movies = movieService.findByDirectorId(id);
        return ResponseEntity.ok(movies);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMovieDto> create(@RequestBody CreateMovieDto dto) {
        Movie movie = movieService.create(dto);
        ResponseMovieDto movieDto = movieService.findById(movie.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(movieDto);
    }



    @GetMapping("/all")
    public ResponseEntity<List<ResponseMovieDto>> findAll() {
        List<ResponseMovieDto> movies = movieService.findAll();
        return ResponseEntity.ok(movies);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseMovieDto> findById(@PathVariable Integer id) {
        ResponseMovieDto dto = movieService.findById(id);
        return ResponseEntity.ok(dto);
    }





    @PutMapping
    public ResponseEntity<ResponseMovieDto> update(@RequestBody CreateMovieDto dto){
        return ResponseEntity.ok(movieService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-by-title-date")
    public ResponseEntity<Void> deleteByTitleAndDate(
            @RequestParam String title, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        movieService.deleteByTitleAndDate(title,date);
        return ResponseEntity.noContent().build();
    }
}
