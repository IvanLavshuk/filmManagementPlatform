package com.film.management.platform.service;

import com.film.management.platform.dto.Request.CreateMovieActorDto;
import com.film.management.platform.dto.Response.ResponseMovieDto;
import com.film.management.platform.dto.Short.ActorRoleDto;
import com.film.management.platform.dto.Short.MovieRoleDto;
import com.film.management.platform.entity.Movie;
import com.film.management.platform.entity.MovieActor;
import com.film.management.platform.mapper.MovieActorMapper;
import com.film.management.platform.repository.ActorRepository;
import com.film.management.platform.repository.MovieActorRepository;
import com.film.management.platform.repository.MovieRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MovieActorService {
    private final MovieActorMapper movieActorMapper;
    private final MovieActorRepository movieActorRepository;
    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;
    @Transactional
    public MovieActor create(CreateMovieActorDto dto){
        Optional<MovieActor> optional = movieActorRepository.findByMovie_IdAndActor_Id(dto.getIdMovie(),dto.getIdActor());
        if(optional.isPresent()){
            throw new IllegalStateException("MovieActor already exists");
        }
        MovieActor movieActor = movieActorMapper.toEntity(dto,movieRepository,actorRepository);
        return movieActorRepository.save(movieActor);
    }
    @Transactional
    public void deleteById(Integer id){
        Optional<MovieActor> optional = movieActorRepository.findById(id);
        if(optional.isEmpty()){
            throw new IllegalStateException("MovieActor already exists");
        }
        movieActorRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<ActorRoleDto> findByMovieId(Integer id){
        return movieActorRepository
                .findByMovie_Id(id)
                .stream()
                .map(movieActorMapper::toActorRoleDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ActorRoleDto> findByTitle(String title){
        return movieActorRepository
                .findByMovie_Title(title)
                .stream()
                .map(movieActorMapper::toActorRoleDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovieRoleDto> findByActorId(Integer id){
        return movieActorRepository
                .findByActor_Id(id)
                .stream()
                .map(movieActorMapper::toMovieRoleDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MovieRoleDto> findByActorRole(String role){
        return movieActorRepository
                .findByActor_Role(role)
                .stream()
                .map(movieActorMapper::toMovieRoleDto)
                .collect(Collectors.toList());
    }

//    @Transactional(readOnly = true)
//    public List<MovieRoleDto> findByActorIdOrderByMovieReleaseDateDesc(Integer id){
//        return movieActorRepository
//                .findByActor_IdOrderByMovie_ReleaseDateDesc(id)
//                .stream()
//                .map(movieActorMapper::toMovieRoleDto)
//                .collect(Collectors.toList());
//    }



    @Transactional(readOnly = true)
    public boolean checkByMovieIdAndActorId(Integer mId,Integer aid){
        return movieActorRepository.findByMovie_IdAndActor_Id(mId,aid).isPresent();
    }

}
