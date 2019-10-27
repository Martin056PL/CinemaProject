package pl.com.tt.restapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.ActorDTO;
import pl.com.tt.restapp.service.ActorService;
import pl.com.tt.restapp.service.MovieService;
import pl.com.tt.restapp.specification.ActorSpec;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class ActorRestController {

    private final ActorService actorService;
    private final MovieService movieService;

    @GetMapping("actors")
    public ResponseEntity<List<Actor>> getAllActors() {
        log.debug("Endpoint address: 'actors' with GET method");
        return new ResponseEntity<>(actorService.findAllActors(), HttpStatus.OK);
    }

    @GetMapping("actors/{id}")
    public ResponseEntity<Actor> getActorByIdFromAllDatabase(@PathVariable Long id) {
        log.debug("Endpoint address: 'actors/actorId' with GET method\nrequests params: actorId = {}", id);
        Optional<Actor> actorFromDataBase = actorService.findActorById(id);
        return actorFromDataBase.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("movies/{movieId}/actors")
    public ResponseEntity<Page<Actor>> getAllActorsFromMovieByIdMovie(@PathVariable(name = "movieId") Long movieId,
                                                                      @RequestParam(name = "name", required = false) String name,
                                                                      @RequestParam(name = "age", required = false) Integer age,
                                                                      Pageable pageable) {
        Optional<Movie> movieFromDataBase = movieService.findMovieById(movieId);
        log.debug("Endpoint address: 'movies/movieId/actors' with GET method\nrequests params: movieId = {}", movieId);
        if (movieFromDataBase.isPresent()) {
            log.debug("Found actors base on movieId = {}", movieId);
            Page<Actor> listOfActors = actorService.findAllActors(ActorSpec.query(movieId, name, age), pageable);
            log.debug("Application returned list of all actors from DB with id: {}", Arrays.toString(listOfActors.stream().map(Actor::getActorId).toArray()));
            return new ResponseEntity<>(listOfActors, HttpStatus.OK);
        } else {
            log.debug("User didn't found movie base on delivered id = {}", movieId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("movies/{movieId}/actors/{actorId}")
    public ResponseEntity<Actor> getProperActorBaseOnActorIdFromMovieByIdMovie(@PathVariable Long movieId,
                                                                               @PathVariable Long actorId) {
        log.debug("Endpoint address: 'movies/movieId/actors/actorId' with GET method\nrequests params: movieId = {} ; actorId = {}", movieId, actorId);
        Optional<Movie> movieFromDataBase = movieService.findMovieById(movieId);
        if (movieFromDataBase.isPresent()) {
            log.debug("Found actors base on movieId = {}", movieId);
            return actorService.findActorByMovieAndActorId(movieFromDataBase.get(), actorId);
        } else {
            log.error("User didn't found movie base on delivered id = {}", movieId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("movies/{movieId}/actors")
    public ResponseEntity<Movie> saveActorToProperMovie(@PathVariable Long movieId,
                                                        @RequestBody ActorDTO actorDTO) throws InvocationTargetException, IllegalAccessException {
        log.debug("Endpoint address: 'movies/movieId/actors' with POST method\nrequests params: movieId = {}, request body: {}", movieId, actorDTO);
        Actor actorTransformedFromActorDTO = actorService.mappingActorDtoToEntity(actorDTO);
        Optional<Movie> movieFromDataBase = movieService.findMovieById(movieId);
        if (movieFromDataBase.isPresent()) {
            log.debug("Found actors base on movieId = {}", movieId);
            return actorService.saveActorToProperMovie(movieFromDataBase.get(), actorTransformedFromActorDTO);
        } else {
            log.error("User didn't found movie base on delivered id = {}", movieId);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("movies/{movieId}/actors/{actorId}")
    public ResponseEntity<Actor> updateActorInProperMovieByMovieId(@PathVariable Long movieId,
                                                                   @PathVariable Long actorId,
                                                                   @Valid @RequestBody ActorDTO actorDTO) throws InvocationTargetException, IllegalAccessException {
        log.debug("Endpoint address: 'movies/movieId/actors/actorId' with PUT method\nrequests params: movieId = {} ; actorId = {}", movieId, actorId);
        log.debug("Request body: {}", actorDTO);
        Actor actorTransformedFromActorDTO = actorService.mappingActorDtoToEntity(actorDTO);
        Optional<Movie> movieFromDatabase = movieService.findMovieById(movieId);
        if (movieFromDatabase.isPresent()) {
            log.debug("Found actors base on movieId = {}", movieId);
            return actorService.updateActorForProperMovie(movieFromDatabase.get(), actorId, actorTransformedFromActorDTO);
        } else {
            log.debug("User didn't found movie base on delivered id = {}", movieId);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("movies/{movieId}/actors/{actorId}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long movieId,
                                             @PathVariable Long actorId) {
        log.debug("Endpoint address: 'movies/movieId/actors/actorId' with DELETE method\nrequests params: movieId = {} ; actorId = {}", movieId, actorId);
        if (movieService.existByMovieId(movieId)) {
            log.debug("Found actors base on movieId = {}", movieId);
            int resultOfDeleteActorOperation = actorService.deleteActor(actorId);
            if (resultOfDeleteActorOperation > 0) {
                log.debug("Successfully deleted actor with id = {}", actorId);
                return ResponseEntity.ok().build();
            } else {
                log.debug("Delete operation has failed, actor id = {}", actorId);
                return ResponseEntity.badRequest().build();
            }
        } else {
            log.debug("User didn't found movie base on delivered id = {}", movieId);
            return ResponseEntity.badRequest().build();
        }
    }
}

