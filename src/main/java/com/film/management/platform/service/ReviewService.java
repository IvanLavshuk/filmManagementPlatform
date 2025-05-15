package com.film.management.platform.service;

import com.film.management.platform.dto.Request.CreateReviewDto;
import com.film.management.platform.dto.Response.ResponseReviewDto;
import com.film.management.platform.entity.Review;
import com.film.management.platform.entity.User;
import com.film.management.platform.mapper.ReviewMapper;
import com.film.management.platform.repository.MovieRepository;
import com.film.management.platform.repository.ReviewRepository;
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
public class ReviewService {
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;
    private MovieRepository movieRepository;
    private ReviewMapper reviewMapper;

    @Transactional
    public Review create(CreateReviewDto reviewDto) {
        String userFullName = reviewDto.getUserFullName();
        String[] parts = userFullName.split(" ");
        String name = parts[0];
        String surname = parts[1];
        if (userRepository.findByNameAndSurname(name, surname).isPresent()) {
            throw new IllegalStateException("Review from this User already exists" + (userFullName));
        }
        Review review = reviewMapper.toEntity(reviewDto, userRepository, movieRepository);
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteById(Integer id) {
        Optional<Review> optional = reviewRepository.findById(id);
        if (optional.isEmpty()) {
            throw new NoSuchElementException("review not found with id: " + id);
        }
        reviewRepository.deleteById(id);
    }


    @Transactional
    public ResponseReviewDto update(CreateReviewDto reviewDto) {
        String userFullName = reviewDto.getUserFullName();
        String[] parts = userFullName.split(" ");
        String name = parts[0];
        String surname = parts[1];
        User user = userRepository.findByNameAndSurname(name, surname).orElseThrow();
        Review review = reviewRepository.findByUser_EmailAndMovie_Title(user.getEmail(), reviewDto.getTitle()).orElseThrow();
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public ResponseReviewDto findById(Integer id) {
        return reviewRepository
                .findById(id)
                .map(reviewMapper::toDto)
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<ResponseReviewDto> findAll() {
        return reviewRepository
                .findAll()
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseReviewDto> findAllByEmail(String email) {
        return reviewRepository
                .findByUser_Email(email)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseReviewDto> findAllByTitle(String title) {
        return reviewRepository
                .findByMovie_Title(title)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseReviewDto> findAllMovieId(Integer id) {
        return reviewRepository
                .findByMovie_Id(id)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseReviewDto> findAllUserId(Integer id) {
        return reviewRepository
                .findByUser_Id(id)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseReviewDto> findAllByRatingGreaterThanEqual(double rating) {
        return reviewRepository
                .findByRatingGreaterThanEqual(rating)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseReviewDto> findAllByRatingLessThanEqual(double rating) {
        return reviewRepository
                .findByRatingLessThanEqual(rating)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseReviewDto> findAllByComment(String text) {
        return reviewRepository
                .findByCommentContainingIgnoreCase(text)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean existsByUserEmail(String email) {
        return reviewRepository.existsByUser_Email(email);
    }
}
