package com.film.management.platform.controller;


import com.film.management.platform.dto.Request.CreateReviewDto;
import com.film.management.platform.dto.Response.ResponseReviewDto;
import com.film.management.platform.entity.Review;
import com.film.management.platform.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/create")
    public ResponseEntity<ResponseReviewDto> create(@RequestBody CreateReviewDto dto) {
        Review review = reviewService.create(dto);
        ResponseReviewDto reviewDto = reviewService.findById(review.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseReviewDto>> findAll() {
        List<ResponseReviewDto> reviews = reviewService.findAll();
        return ResponseEntity.ok(reviews);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseReviewDto> findById(@PathVariable Integer id) {
        ResponseReviewDto dto = reviewService.findById(id);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/email")
    public ResponseEntity<List<ResponseReviewDto>> findByEmail(@RequestParam String email){
        List<ResponseReviewDto> reviews = reviewService.findAllByEmail(email);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/title")
    public ResponseEntity<List<ResponseReviewDto>> findByTitle(@RequestParam String title){
        List<ResponseReviewDto> reviews = reviewService.findAllByTitle(title);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/text")
    public ResponseEntity<List<ResponseReviewDto>> findByText(@RequestParam String text){
        List<ResponseReviewDto> reviews = reviewService.findAllByComment(text);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/movie-id")
    public ResponseEntity<List<ResponseReviewDto>> findByMovieId(@PathVariable Integer id){
        List<ResponseReviewDto> reviews = reviewService.findAllMovieId(id);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user-id")
    public ResponseEntity<List<ResponseReviewDto>> findByUserId(@PathVariable Integer id){
        List<ResponseReviewDto> reviews = reviewService.findAllUserId(id);
        return ResponseEntity.ok(reviews);
    }


    @GetMapping("/greater-than")
    public ResponseEntity<List<ResponseReviewDto>> findGreaterThan(@RequestParam double rating){
        List<ResponseReviewDto> reviews = reviewService.findAllByRatingGreaterThanEqual(rating);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/less-than")
    public ResponseEntity<List<ResponseReviewDto>> findLessThan(@RequestParam double rating){
        List<ResponseReviewDto> reviews = reviewService.findAllByRatingLessThanEqual(rating);
        return ResponseEntity.ok(reviews);
    }

    @PutMapping
    public ResponseEntity<ResponseReviewDto> update(@RequestBody CreateReviewDto dto){
        return ResponseEntity.ok(reviewService.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
