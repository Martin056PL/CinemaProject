package pl.com.tt.restapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.MovieDTO;
import pl.com.tt.restapp.repository.MovieRepository;
import pl.com.tt.restapp.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;
    private final Utils utils;

    @Override
    public Movie mappingMovieDtoToEntity(MovieDTO dto) throws InvocationTargetException, IllegalAccessException {
        return (Movie) utils.mapperFromDtoToEntity(dto);
    }

    @Override
    public Page<Movie> findAllMovies(Specification<Movie> specification, Pageable pageable){
        return repository.findAll(specification, pageable);
    }

    @Override
    public List<Movie> findAllMovies() {
        return repository.findAll();
    }

    @Override
    public Optional<Movie> findMovieById(Long id) {
        Movie movieFromDataBase = repository.findAllByMovieId(id);
        return Optional.ofNullable(movieFromDataBase);
    }

    @Override
    public Boolean existByMovieId(Long id){
        return repository.existsByMovieId(id);
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return repository.save(movie);
    }

    @Override
    public void deleteMovieById(Long id) {
        repository.deleteById(id);
    }


}