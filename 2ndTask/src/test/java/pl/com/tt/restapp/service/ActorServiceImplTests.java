package pl.com.tt.restapp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.ActorDTO;
import pl.com.tt.restapp.repository.ActorRepository;
import pl.com.tt.restapp.specification.ActorSpec;
import pl.com.tt.restapp.utils.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ActorServiceImplTests {

    @Mock
    ActorRepository repository;

    @Mock
    Actor actor;

    @Mock
    ActorDTO actorDTO;

    @Mock
    Movie movie;

    @Mock
    Pageable pageable;

    @Mock
    Utils utils;

    @Mock
    MovieServiceImpl movieService;

    @InjectMocks
    ActorServiceImpl service;

    private static final Long ID = 1L;
    private static final String ACTORNAME = "Name";
    private static final int ACTORAGE = 30;

    @Test
    public void should_verify_find_all_actors_by_movie_pageable_method(){
        service.findAllActorsByMovie(movie,pageable);
        verify(repository).findAllByMovie(movie,pageable);
    }

    @Test
    public void should_verify_findAll_method_with_specification_and_pageable(){
        Specification<Actor> spec = ActorSpec.query(ID, ACTORNAME, ACTORAGE);
        service.findAllActors(spec, pageable);
        verify(repository).findAll(spec,pageable);
    }

    @Test
    public void should_verify_find_actor_by_movie_and_actor_id_method(){
        service.findActorByMovieAndActorId(movie,ID);
        verify(repository).findActorByMovieAndActorId(movie, ID);
    }
    
    @Test
    public void should_return_status_ok_when_service_returns_actor_base_on_movie_and_actor_id(){
        service.findActorByMovieAndActorId(movie,ID);
        when(repository.findActorByMovieAndActorId(movie, ID)).thenReturn(actor);
        assertEquals(HttpStatus.OK,service.findActorByMovieAndActorId(movie,ID).getStatusCode());
    }

    @Test
    public void should_return_status_bad_request_when_service_tries_return_actor_which_does_not_exist_base_on_movie_and_actor_id(){
        service.findActorByMovieAndActorId(movie,ID);
        when(repository.findActorByMovieAndActorId(movie, ID)).thenReturn(null);
        assertEquals(HttpStatus.BAD_REQUEST,service.findActorByMovieAndActorId(movie,ID).getStatusCode());
    }

    @Test
    public void should_verify_find_actor_by_id_method() {
        service.findActorById(ID);
        verify(repository).findAllByActorId(ID);
    }

    @Test
    public void should_verify_find_all_actors_method() {
        service.findAllActors();
        verify(repository).findAll();
    }

    @Test
    public void should_verify_save_actor_to_proper_movie_method() {
        service.saveActorToProperMovie(movie, actor);
        verify(movieService).saveMovie(movie);
    }

    @Test
    public void should_verify_update_actor_for_proper_movie_which_exist_method() {
        when(movie.getActors()).thenReturn(exampleDataToTest());
        when(actor.getActorId()).thenReturn(1L);
        service.updateActorForProperMovie(movie, ID, actor);
        verify(repository).save(actor);
    }

    @Test
    public void should_verify_update_actor_for_proper_movie_which_does_not_exist_method() {
        when(movie.getActors()).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.BAD_REQUEST,service.updateActorForProperMovie(movie, ID, actor).getStatusCode());
    }

    @Test
    public void should_verify_delete_actor_method() {
        service.deleteActor(ID);
        verify(repository).deleteActorsByActorId(ID);
    }

    @Test
    public void should_verify_mapping_actor_dto_to_entity_method() throws InvocationTargetException, IllegalAccessException {
        service.mappingActorDtoToEntity(actorDTO);
        verify(utils).mapperFromDtoToEntity(actorDTO);
    }

    private List<Actor> exampleDataToTest(){
        List<Actor> list = new ArrayList<>();
        list.add(actor);
        return list;
    }

}
