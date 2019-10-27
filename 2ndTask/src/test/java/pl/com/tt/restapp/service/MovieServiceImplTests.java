package pl.com.tt.restapp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.MovieDTO;
import pl.com.tt.restapp.repository.MovieRepository;
import pl.com.tt.restapp.specification.MovieSpecification;
import pl.com.tt.restapp.utils.Utils;

import java.lang.reflect.InvocationTargetException;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTests {

    @Mock
    MovieRepository repository;

    @Mock
    Utils utils;

    @Mock
    Movie movie;

    @Mock
    Pageable pageable;

    @Mock
    MovieDTO movieDTO;

    @Mock
    MovieSpecification movieSpecification;

    @InjectMocks
    MovieServiceImpl service;

    private static final long ID = 1;

    @Test
    public void should_verify_find_movie_by_id_method() {
        service.findMovieById(ID);
        verify(repository).findAllByMovieId(ID);
    }

    @Test
    public void should_verify_find_all_movies_pageable_method() {
        service.findAllMovies(movieSpecification, pageable);
        verify(repository).findAll(movieSpecification, pageable);
    }

    @Test
    public void should_verify_saving_movies_method() {
        service.saveMovie(movie);
        verify(repository).save(movie);
    }

    @Test
    public void should_verify_deleting_movies_method() {
        service.deleteMovieById(ID);
        verify(repository).deleteById(ID);
    }

    @Test
    public void should_verify_map_from_DTO_to_Entity_method() throws InvocationTargetException, IllegalAccessException {
        service.mappingMovieDtoToEntity(movieDTO);
        verify(utils).mapperFromDtoToEntity(movieDTO);
    }

    @Test
    public void should_verify_exist_by_movie_id_method(){
        service.existByMovieId(ID);
        verify(repository).existsByMovieId(ID);
    }



}
