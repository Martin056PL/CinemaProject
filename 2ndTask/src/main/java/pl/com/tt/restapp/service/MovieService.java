package pl.com.tt.restapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.MovieDTO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

public interface MovieService {

    Movie mappingMovieDtoToEntity(MovieDTO dto) throws InvocationTargetException, IllegalAccessException;

    Page<Movie> findAllMovies(Specification<Movie> specification, Pageable pageable);

    List<Movie> findAllMovies();

    Optional<Movie> findMovieById(Long id);

    Boolean existByMovieId(Long id);

    Movie saveMovie(Movie movie);

    void deleteMovieById(Long id);


}
