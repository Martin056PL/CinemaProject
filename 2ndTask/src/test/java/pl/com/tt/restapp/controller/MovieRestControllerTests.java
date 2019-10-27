package pl.com.tt.restapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.MovieDTO;
import pl.com.tt.restapp.service.MovieServiceImpl;
import pl.com.tt.restapp.specification.MovieSpecification;

import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieRestControllerTests {

    @Mock
    Movie movie;

    @Mock
    MovieDTO movieDTO;

    @Mock
    Pageable pageable;

    @Mock
    MovieServiceImpl movieService;

    @Mock
    Page<Movie> page;

    @Mock
    MovieSpecification movieSpecification;

    @InjectMocks
    MovieRestController movieController;

    private static final Long ID = 23L;

    //get
    @Test
    public void should_return_status_ok_when_controller_returns_movie_list() {
        when(movieService.findAllMovies(movieSpecification, pageable)).thenReturn(page);
        assertEquals(HttpStatus.OK, movieController.getAllMovies(movieSpecification, pageable).getStatusCode());
    }

    @Test
    public void should_return_status_ok_when_controller_returns_movie_by_movie_id_which_exist() {
        when(movieService.findMovieById(ID)).thenReturn(Optional.of(movie));
        assertEquals(HttpStatus.OK, movieController.getMovieById(ID).getStatusCode());
    }

    @Test
    public void should_body_response_be_equal_to_movie_which_was_returned_by_controller() {
        when(movieService.findMovieById(ID)).thenReturn(Optional.of(movie));
        assertEquals(movie, movieController.getMovieById(ID).getBody());
    }

    @Test
    public void should_return_status_not_found_when_controller_tries_return_movie_base_on_movie_id_which_does_not_exist() {
        when(movieService.findMovieById(ID)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, movieController.getMovieById(ID).getStatusCode());
    }

    //post
    @Test
    public void should_return_status_created_when_controller_adds_new_movie_successfully() throws URISyntaxException, InvocationTargetException, IllegalAccessException {
        when(movieService.mappingMovieDtoToEntity(movieDTO)).thenReturn(movie);
        when(movieService.saveMovie(movie)).thenReturn(movie);
        assertEquals(HttpStatus.CREATED, movieController.saveMovie(movieDTO).getStatusCode());
    }

    //put
    @Test
    public void should_return_status_ok_when_controller_updates_movie_base_on_existing_movie_id() throws InvocationTargetException, IllegalAccessException {
        when(movieService.mappingMovieDtoToEntity(movieDTO)).thenReturn(movie);
        when(movieService.existByMovieId(ID)).thenReturn(true);
        assertEquals(HttpStatus.OK, movieController.updateMovie(ID, movieDTO).getStatusCode());
    }

    @Test
    public void should_return_status_bad_request_when_controller_will_not_update_movie_base_on_movie_id_which_does_not_exist() throws InvocationTargetException, IllegalAccessException {
        when(movieService.mappingMovieDtoToEntity(movieDTO)).thenReturn(movie);
        when(movieService.existByMovieId(ID)).thenReturn(false);
        assertEquals(HttpStatus.BAD_REQUEST, movieController.updateMovie(ID, movieDTO).getStatusCode());
    }

    //delete
    @Test
    public void should_return_status_ok_when_controller_deletes_movie_base_on_movie_id_which_exist() {
        when(movieService.existByMovieId(ID)).thenReturn(true);
        assertEquals(HttpStatus.OK, movieController.deleteMovie(ID).getStatusCode());
    }

    @Test
    public void should_return_status_bad_request_when_controller_tries_to_delete_movie_base_on_movie_id_which_does_not_exist() {
        when(movieService.existByMovieId(ID)).thenReturn(false);
        assertEquals(HttpStatus.BAD_REQUEST, movieController.deleteMovie(ID).getStatusCode());
    }


}
