package com.film.management.platform.service;

import com.film.management.platform.dto.Request.CreateMovieDto;
import com.film.management.platform.dto.Response.ResponseMovieDto;
import com.film.management.platform.entity.Movie;
import com.film.management.platform.mapper.MovieActorMapper;
import com.film.management.platform.mapper.MovieMapper;
import com.film.management.platform.repository.*;
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
public class MovieService {
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final GenreRepository genreRepository;
    private final ReviewRepository reviewRepository;
    private final MovieMapper movieMapper;
    private final MovieActorMapper movieActorMapper;

    public Double getAverageRating(Integer movieId) {
        return reviewRepository.findAverageRatingByMovieId(movieId);
    }

    @Transactional
    public Movie create(CreateMovieDto movieDto) {
        Optional<Movie> optional = movieRepository.findByTitleAndReleaseDate(movieDto.getTitle(), movieDto.getReleaseDate());
        if (optional.isPresent()) {
            throw new IllegalStateException("Movie with this title and releaseDate already exists" +
                    "(" + movieDto.getTitle() + movieDto.getReleaseDate() + ")");
        }
        Movie movie = movieMapper.toEntity(movieDto, movieActorMapper,
                movieRepository, actorRepository, genreRepository, directorRepository);
        return movieRepository.save(movie);
    }

    @Transactional
    public ResponseMovieDto update(CreateMovieDto movieDto) {
        Optional<Movie> optional = movieRepository.findByTitleAndReleaseDate(movieDto.getTitle(), movieDto.getReleaseDate());
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Movie with this title and releaseDate does not exists" +
                    "(" + movieDto.getTitle() + movieDto.getReleaseDate() + ")");
        }
        Movie movie = movieRepository.findByTitleAndReleaseDate(movieDto.getTitle(), movieDto.getReleaseDate()).orElseThrow();
        return movieMapper.toDto(movieRepository.save(movie));
    }

    @Transactional
    public void deleteById(Integer id) {
        Optional<Movie> optional = movieRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Movie with this id does not exists");
        }
        movieRepository.deleteById(id);
    }

    @Transactional
    public void deleteByTitleAndDate(String title, LocalDate releaseDate) {
        Optional<Movie> optional = movieRepository.findByTitleAndReleaseDate(title, releaseDate);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Movie with this id does not exists");
        }
        movieRepository.deleteByTitleAndReleaseDate(title, releaseDate);
    }

    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findAll() {
        return movieRepository
                .findAll()
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findByMovieAndDate(String title, LocalDate releaseDate) {
        return movieRepository
                .findByTitleAndReleaseDate(title, releaseDate)
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseMovieDto findById(Integer id) {
        return movieRepository
                .findById(id)
                .map(movie -> movieMapper.toDto(movie))
                .orElseThrow(() -> new EntityNotFoundException("Movie with this id does not exists"));
    }
    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findByDirectorId(Integer id) {
        return movieRepository
                .findByDirectors_Id(id)
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findByTitle(String title) {
        return movieRepository
                .findByTitleContainingIgnoreCase(title)
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findByPrefix(String title) {
        return movieRepository
                .findByTitleStartingWith(title)
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findByCountry(String country) {
        return movieRepository
                .findByCountryIgnoreCase(country)
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findByGenre(String Genre) {
        return movieRepository
                .findByGenres_NameIgnoreCase(Genre)
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findByDirector(String name, String surname) {
        return movieRepository
                .findByDirectors_NameIgnoreCaseAndDirectors_SurnameIgnoreCase(name, surname)
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findByDateBetween(LocalDate start, LocalDate end) {
        return movieRepository
                .findByReleaseDateBetween(start, end)
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findAllByOrderByDateDesc() {
        return movieRepository
                .findAllByOrderByReleaseDateDesc()
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findAllByOrderByDateAsc() {
        return movieRepository
                .findAllByOrderByReleaseDateAsc()
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseMovieDto> findAllOrderByAverageRatingDesc() {
        return movieRepository
                .findAllOrderByAverageRatingDesc()
                .stream()
                .map(movie -> movieMapper.toDto(movie))
                .collect(Collectors.toList());
    }
}
