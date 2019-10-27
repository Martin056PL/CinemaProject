package pl.com.tt.restapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.MovieDTO;
import pl.com.tt.restapp.service.MovieService;
import pl.com.tt.restapp.specification.MovieSpecification;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class MovieRestController {

    private final MovieService movieService;

    @GetMapping("movies")
    public ResponseEntity<Page<Movie>> getAllMovies(MovieSpecification movieSpecification, Pageable pageable) {
        Page <Movie> listOfAllMoviesFromDB = movieService.findAllMovies(movieSpecification, pageable);
        log.debug("Endpoint address: 'movies' with GET method, request parameters: specification: {} ; pageable: {}", movieSpecification,pageable);
        log.debug("Application returned list of all movies from DB with id: {}", Arrays.toString(listOfAllMoviesFromDB.stream().map(Movie::getMovieId).toArray()));
        return new ResponseEntity<>(listOfAllMoviesFromDB, HttpStatus.OK);
    }

    @GetMapping("movies/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        log.debug("Endpoint address: 'movies/{movieId}' with GET method\nrequests params: movieId = {}", id);
        Optional<Movie> movieFromDataBase = movieService.findMovieById(id);
        return movieFromDataBase.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("movies")
    public ResponseEntity<Movie> saveMovie(@Valid @RequestBody MovieDTO movieDTO) throws URISyntaxException, InvocationTargetException, IllegalAccessException {
        log.debug("Endpoint address: 'movies' with POST method\nrequest body: {}", movieDTO);
        Movie movieTransformedFromDTO = movieService.mappingMovieDtoToEntity(movieDTO);
        Movie result = movieService.saveMovie(movieTransformedFromDTO);
        return ResponseEntity.created(new URI("add-movie" + result.getMovieId()))
                .body(result);
    }

    @PutMapping("movies/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable Long id,
                                             @Valid @RequestBody MovieDTO movieDTO) throws InvocationTargetException, IllegalAccessException {
        log.debug("Endpoint address: 'movies/movieId' with PUT method\nrequests params: movieId = {}", id);
        log.debug("Request body: {}", movieDTO);
        Movie movieTransformedFromDTO = movieService.mappingMovieDtoToEntity(movieDTO);
        if (movieService.existByMovieId(id)) {
            log.debug("Found movie base on movieId = {}", id);
            movieTransformedFromDTO.setMovieId(id);
            Movie result = movieService.saveMovie(movieTransformedFromDTO);
            return ResponseEntity.ok().body(result);
        } else {
            log.debug("User didn't found movie base on delivered id = {}", id);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("movies/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable Long id) {
        log.debug("Endpoint address: 'movies/id' with DELETE method\nrequests params: movieId = {}", id);
        if (movieService.existByMovieId(id)) {
            log.debug("Found movie base on movieId = {}", id);
            movieService.deleteMovieById(id);
            return ResponseEntity.ok().build();
        } else {
            log.debug("User didn't found movie base on delivered id = {}", id);
            return ResponseEntity.badRequest().build();
        }
    }
}

