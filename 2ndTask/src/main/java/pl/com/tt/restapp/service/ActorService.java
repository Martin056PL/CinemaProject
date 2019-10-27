package pl.com.tt.restapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.ActorDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface ActorService {

    Actor mappingActorDtoToEntity(ActorDTO actorDTO) throws InvocationTargetException, IllegalAccessException;

    List<Actor> findAllActors();

    Page<Actor> findAllActors(Specification<Actor> specification, Pageable pageable);

    Optional<Actor> findActorById(Long id);

    Page<Actor> findAllActorsByMovie(Movie movie, Pageable pageable);

    ResponseEntity<Actor> findActorByMovieAndActorId(Movie movie, Long actorId);

    ResponseEntity<Movie> saveActorToProperMovie(Movie movieFromDatabase, Actor actorJSON);

    ResponseEntity<Actor> updateActorForProperMovie(Movie movieFromDatabase, Long actorId, Actor actorJSON);

    int deleteActor(Long id);
}
