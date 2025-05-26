package com.film.management.platform.service;

import com.film.management.platform.dto.Request.CreateReviewDto;
import com.film.management.platform.dto.Response.ResponseReviewDto;
import com.film.management.platform.entity.Review;
import com.film.management.platform.entity.User;
import com.film.management.platform.mapper.ReviewMapper;
import com.film.management.platform.repository.MovieRepository;
import com.film.management.platform.repository.ReviewRepository;
import com.film.management.platform.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    public Review create(CreateReviewDto reviewDto) {
        if(reviewDto==null){
            throw new IllegalStateException("CreateReviewDTO from this User is null" );
        }
        String userFullName = reviewDto.getUserFullName();
        String[] parts = userFullName.split(" ");
        String name = parts[0];
        String surname = parts[1];
        String rev = reviewDto.getTitle();

        if (reviewRepository.findByUser_NameAndUser_SurnameAndMovie_Title(name, surname,rev).isPresent()) {
            throw new IllegalStateException("Review from this User already exists " + (userFullName));
        }
        Review review = reviewMapper.toEntity(reviewDto, userRepository, movieRepository);
        review.setDate(LocalDate.now());
        return reviewRepository.save(review);
    }

    @Transactional
    public void deleteById(Integer id) {
        Optional<Review> optional = reviewRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("review not found with id: " + id);
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
        Review review = reviewRepository.
                findByUser_EmailAndMovie_Title(user.getEmail(), reviewDto.getTitle()).
                orElseThrow(() ->
                        new EntityNotFoundException("review with this users email and movie's title does not exist"));
        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public ResponseReviewDto findById(Integer id) {
        return reviewRepository
                .findById(id)
                .map(reviewMapper::toDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("review with this id does not exist"));
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
                .findByTextContainingIgnoreCase(text)
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public boolean existsByUserEmail(String email) {
        return reviewRepository.existsByUser_Email(email);
    }
}
