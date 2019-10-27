package pl.com.tt.restapp.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.ActorDTO;
import pl.com.tt.restapp.service.ActorServiceImpl;
import pl.com.tt.restapp.service.MovieServiceImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ActorRestControllerTests {

    @Mock
    Actor actor;

    @Mock
    ActorDTO actorDTO;

    @Mock
    Movie movie;

    @Mock
    ActorServiceImpl actorService;

    @Mock
    MovieServiceImpl movieService;

    @Mock
    Pageable pageable;

    @Mock
    Page<Actor> page;

    @InjectMocks
    ActorRestController controller;

    private static final Long actorID = 1L;
    private static final Long movieID = 1L;
    private static final String actorName = "Some Actor";
    private static final Integer actorAge = 23;

    @Test
    public void should_return_status_ok_when_controller_returns_all_actors() {
        assertEquals(HttpStatus.OK, controller.getAllActors().getStatusCode());
    }

    @Test
    public void should_movie_list_has_the_same_size_as_movie_list_returned_from_controller() {
        when(actorService.findAllActors()).thenReturn(Collections.singletonList(actor));
        assertEquals(Collections.singleton(actor).size(), controller.getAllActors().getBody().size());
    }

    @Test
    public void should_return_status_ok_when_controller_returns_actor_base_on_id_which_exist() {
        when(actorService.findActorById(actorID)).thenReturn(Optional.of(actor));
        assertEquals(HttpStatus.OK, controller.getActorByIdFromAllDatabase(actorID).getStatusCode());
    }

    @Test
    public void should_return_status_not_found_when_controller_does_not_return_actor_base_on_id_which_does_not_exist() {
        when(actorService.findActorById(actorID)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, controller.getActorByIdFromAllDatabase(actorID).getStatusCode());
    }

    @Test
    public void should_body_response_be_equal_to_response_given_by_controller_based_on_actor_id() {
        when(actorService.findActorById(actorID)).thenReturn(Optional.of(actor));
        assertEquals(actor, controller.getActorByIdFromAllDatabase(actorID).getBody());
    }

    @Test
    public void should_return_status_ok_when_controller_returns_all_actors_by_movie_id_which_exists() {
        when(movieService.findMovieById(movieID)).thenReturn(Optional.of(movie));
        when(actorService.findAllActors(any(),eq(pageable))).thenReturn(page);
        assertEquals(HttpStatus.OK, controller.getAllActorsFromMovieByIdMovie(movieID,actorName,actorAge,pageable).getStatusCode());
    }


    @Test
    public void should_return_status_bed_request_when_controller_does_not_returns_actors_by_movie_id_which_does_not_exist() {
        when(movieService.findMovieById(movieID)).thenReturn(Optional.empty());
        when(actorService.findAllActorsByMovie(movie,pageable)).thenReturn(page);
        assertEquals(HttpStatus.NOT_FOUND, controller.getAllActorsFromMovieByIdMovie(movieID,actorName, actorAge, pageable).getStatusCode());
    }

    @Test
    public void should_return_status_ok_when_controller_returns_actor_by_movie_id_which_exist_and_actor_id_which_also_exist(){
        when(movieService.findMovieById(movieID)).thenReturn(Optional.of(movie));
        when(actorService.findActorByMovieAndActorId(movie,actorID)).thenReturn(ResponseEntity.ok().build());
        assertEquals(HttpStatus.OK,controller.getProperActorBaseOnActorIdFromMovieByIdMovie(movieID,actorID).getStatusCode());
    }

    @Test
    public void should_return_status_not_found_when_controller_does_not_return_actor_by_movie_id_which_does_not_exist_and_actor_id_which_exist(){
        when(movieService.findMovieById(movieID)).thenReturn(Optional.empty());
        when(actorService.findActorByMovieAndActorId(movie,actorID)).thenReturn(ResponseEntity.ok().build());
        assertEquals(HttpStatus.NOT_FOUND,controller.getProperActorBaseOnActorIdFromMovieByIdMovie(movieID,actorID).getStatusCode());
    }

    //post
    @Test
    public void should_return_status_ok_when_controller_add_actor_to_movie_by_movie_id() throws InvocationTargetException, IllegalAccessException {
        when(movieService.findMovieById(movieID)).thenReturn(Optional.of(movie));
        when(actorService.mappingActorDtoToEntity(actorDTO)).thenReturn(actor);
        when(actorService.saveActorToProperMovie(movie,actor)).thenReturn(ResponseEntity.ok().build());
        assertEquals(HttpStatus.OK,controller.saveActorToProperMovie(movieID,actorDTO).getStatusCode());
    }

    @Test
    public void should_return_status_bad_request_when_controller_tries_add_actor_to_movie_by_not_existing_movie_id() throws InvocationTargetException, IllegalAccessException {
        when(movieService.findMovieById(movieID)).thenReturn(Optional.empty());
        when(actorService.mappingActorDtoToEntity(actorDTO)).thenReturn(actor);
        when(actorService.saveActorToProperMovie(movie,actor)).thenReturn(ResponseEntity.badRequest().build());
        assertEquals(HttpStatus.BAD_REQUEST,controller.saveActorToProperMovie(movieID,actorDTO).getStatusCode());
    }

    //put
    @Test
    public void should_return_status_ok_when_controller_update_actor_by_movie_id_which_exist_and_actor_id_which_exist() throws InvocationTargetException, IllegalAccessException {
        when(movieService.findMovieById(movieID)).thenReturn(Optional.of(movie));
        when(actorService.mappingActorDtoToEntity(actorDTO)).thenReturn(actor);
        when(actorService.updateActorForProperMovie(movie, actorID, actor)).thenReturn(ResponseEntity.ok(actor));
        assertEquals(HttpStatus.OK, controller.updateActorInProperMovieByMovieId(movieID, actorID, actorDTO).getStatusCode());
    }

    @Test
    public void should_return_status_bad_request_when_controller_tries_update_actor_by_movie_id_which_does_not_exist_and_actor_id_which_exist() throws InvocationTargetException, IllegalAccessException {
        when(movieService.findMovieById(movieID)).thenReturn(Optional.empty());
        when(actorService.mappingActorDtoToEntity(actorDTO)).thenReturn(actor);
        when(actorService.updateActorForProperMovie(movie, actorID, actor)).thenReturn(ResponseEntity.badRequest().build());
        assertEquals(HttpStatus.BAD_REQUEST, controller.updateActorInProperMovieByMovieId(movieID, actorID, actorDTO).getStatusCode());
    }

    //delete
    @Test
    public void should_return_status_ok_when_controller_deletes_actor_by_movie_id_which_exist_and_actor_id_which_exists() {
        when(movieService.existByMovieId(movieID)).thenReturn(true);
        when(actorService.deleteActor(actorID)).thenReturn(1);
        assertEquals(HttpStatus.OK, controller.deleteMovie(movieID, actorID).getStatusCode());
    }

    @Test
    public void should_return_status_bad_request_when_controller_tries_delete_actor_by_movie_id_which_do_not_exist_and_actor_id_which_exists() {
        when(movieService.existByMovieId(movieID)).thenReturn(false);
        when(actorService.deleteActor(actorID)).thenReturn(1);
        assertEquals(HttpStatus.BAD_REQUEST, controller.deleteMovie(movieID, actorID).getStatusCode());
    }

    @Test
    public void should_return_status_bad_request_when_controller_deletes_actor_by_movie_id_which_exist_and_actor_id_which_does_not_exist() {
        when(movieService.existByMovieId(movieID)).thenReturn(true);
        when(actorService.findActorById(actorID)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.BAD_REQUEST, controller.deleteMovie(movieID, actorID).getStatusCode());
    }

    @Test
    public void should_return_status_bad_request_when_controller_tries_delete_actor_by_movie_id_which_do_not_exist_and_actor_id_which_does_not_exist() {
        when(movieService.existByMovieId(movieID)).thenReturn(false);
        when(actorService.findActorById(actorID)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.BAD_REQUEST, controller.deleteMovie(movieID, actorID).getStatusCode());
    }

}
