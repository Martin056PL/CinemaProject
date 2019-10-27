package pl.com.tt.restapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.ActorDTO;
import pl.com.tt.restapp.repository.ActorRepository;
import pl.com.tt.restapp.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {

    private final ActorRepository repository;
    private final MovieService movieService;
    private final Utils utils;

    @Override
    public Actor mappingActorDtoToEntity(ActorDTO actorDTO) throws InvocationTargetException, IllegalAccessException {
        return (Actor) utils.mapperFromDtoToEntity(actorDTO);
    }

    @Override
    public List<Actor> findAllActors() {
        return repository.findAll();
    }

    @Override
    public Page<Actor> findAllActors(Specification<Actor> actorSpec, Pageable pageable) {
        return repository.findAll(actorSpec, pageable);
    }

    @Override
    public Optional<Actor> findActorById(Long id) {
        Actor movieFromDataBase = repository.findAllByActorId(id);
        return Optional.ofNullable(movieFromDataBase);
    }

    @Override
    public Page<Actor> findAllActorsByMovie(Movie movie, Pageable pageable) {
        return repository.findAllByMovie(movie, pageable);
    }

    @Override
    public ResponseEntity<Actor> findActorByMovieAndActorId(Movie movie, Long actorId) {
        Optional<Actor> optionalActor = Optional.ofNullable(repository.findActorByMovieAndActorId(movie, actorId));
        if (optionalActor.isPresent()) {
            return ResponseEntity.ok(optionalActor.get());
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<Movie> saveActorToProperMovie(Movie movieFromDatabase, Actor actorJSON) {
        movieFromDatabase.getActors().add(actorJSON);
        Movie result = movieService.saveMovie(movieFromDatabase);
        return ResponseEntity.ok().body(result);
    }

    @Override
    public ResponseEntity<Actor> updateActorForProperMovie(Movie movieFromDatabase, Long actorId, Actor actorJSON) {
        List<Actor> actorsList = movieFromDatabase.getActors();
        Optional<Actor> optionalActorFromDataBaseBaseOnId = actorsList.stream()
                .filter(actor -> actor.getActorId().equals(actorId)).findAny();
        if (optionalActorFromDataBaseBaseOnId.isPresent()) {
            Actor result = repository.save(actorJSON);
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public int deleteActor(Long actorId) {
        return repository.deleteActorsByActorId(actorId);
    }


}



