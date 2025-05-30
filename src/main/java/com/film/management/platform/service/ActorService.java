package com.film.management.platform.service;

import com.film.management.platform.dto.Request.CreateActorDto;
import com.film.management.platform.dto.Response.ResponseActorDto;
import com.film.management.platform.entity.Actor;
import com.film.management.platform.mapper.ActorMapper;
import com.film.management.platform.repository.ActorRepository;
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
public class ActorService {
    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    @Transactional
    public Actor create(CreateActorDto actorDto) {
        Optional<Actor> optional = actorRepository.
                findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(actorDto.getName(), actorDto.getSurname());
        if (optional.isPresent()) {
            throw new IllegalStateException("Actor with this name and surname already exists");
        }
        Actor actor = actorMapper.toEntity(actorDto);
        return actorRepository.save(actor);
    }

    @Transactional(readOnly = true)
    public List<ResponseActorDto> findAll() {
        return actorRepository
                .findAll()
                .stream()
                .map(actorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ResponseActorDto findById(Integer id) {
        return actorRepository
                .findById(id)
                .map(actorMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Actor with this id is not exist"));
    }

    @Transactional(readOnly = true)
    public List<ResponseActorDto> findAllWithSurname(String surname) {
        return actorRepository
                .findBySurnameContainingIgnoreCase(surname)
                .stream()
                .map(actorMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ResponseActorDto> findAllWithName(String name) {
        return actorRepository
                .findByNameContainingIgnoreCase(name)
                .stream()
                .map(actorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResponseActorDto> findAllByBirthdateBefore(LocalDate localDate){
        return actorRepository
                .findByBirthdateBefore(localDate)
                .stream()
                .map(actorMapper::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public ResponseActorDto update(CreateActorDto actorDto) {
        String name = actorDto.getName();
        String surname = actorDto.getSurname();
        Actor actor = actorRepository
                .findByNameContainingIgnoreCaseAndSurnameContainingIgnoreCase(name, surname)
                .orElseThrow(() -> new EntityNotFoundException("Actor with this name and surname is not exist"));
        return actorMapper.toDto(actorRepository.save(actor));
    }
    @Transactional
    public void deleteById(Integer id){
        Optional<Actor> optional = actorRepository.findById(id);
        if(optional.isEmpty()){
            throw new EntityNotFoundException("actor not found with id: " + id);
        }
        actorRepository.deleteById(id);
    }
}
