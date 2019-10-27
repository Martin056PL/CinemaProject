package pl.com.tt.restapp.utils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.ActorDTO;
import pl.com.tt.restapp.dto.MovieDTO;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UtilsTests {

    private Utils utils;

    @Before
    public void intMapper() {
        utils = new Utils();
    }

    @Test
    public void should_transfer_data_from_movieDTO_to_movie_entity() throws InvocationTargetException, IllegalAccessException {
        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setTitle("Rezerwowe Psy");
        movieDTO.setType("Akcja");
        movieDTO.setActors(Collections.emptyList());
        movieDTO.setDatePremiere(LocalDate.parse("2000-12-12"));

        Movie movie = (Movie) utils.mapperFromDtoToEntity(movieDTO);

        assertEquals(movieDTO.getTitle(), movie.getTitle());
        assertEquals(movieDTO.getType(), movie.getType());
        assertEquals(movieDTO.getActors(), movie.getActors());
        assertEquals(movieDTO.getDatePremiere(), movie.getDatePremiere());
    }

    @Test
    public void should_transfer_data_from_actorDTO_to_actor_entity() throws InvocationTargetException, IllegalAccessException {
        ActorDTO actorDTO = new ActorDTO();

        actorDTO.setFirstName("Tomek");
        actorDTO.setLastName("Kowalski");
        actorDTO.setAge(12);

        Actor actor = (Actor) utils.mapperFromDtoToEntity(actorDTO);

        assertEquals(actorDTO.getFirstName(), actor.getFirstName());
        assertEquals(actorDTO.getLastName(), actor.getLastName());
        assertEquals(actorDTO.getAge(), actor.getAge());
    }

    @Test(expected = IllegalAccessException.class)
    public void should_throw_Illegal_argument_exception() throws InvocationTargetException, IllegalAccessException {
        Object o = new Object();
        utils.mapperFromDtoToEntity(o);
    }


    @Test
    public void should_transfer_data_from_movie_entity_to_movie_DTO() throws InvocationTargetException, IllegalAccessException {
        Movie movie = new Movie();

        movie.setTitle("Rezerwowe Psy");
        movie.setType("Akcja");
        movie.setActors(Collections.emptyList());
        movie.setDatePremiere(LocalDate.parse("2000-12-12"));

        MovieDTO movieDTO = (MovieDTO) utils.mapperFromEntityToDto(movie);

        assertEquals(movie.getTitle(), movieDTO.getTitle());
        assertEquals(movie.getType(), movieDTO.getType());
        assertEquals(movie.getActors(), movieDTO.getActors());
        assertEquals(movie.getDatePremiere(), movieDTO.getDatePremiere());
    }

    @Test
    public void should_transfer_data_from_actor_entity_to_actor_DTO() throws InvocationTargetException, IllegalAccessException {
        Actor actor = new Actor();

        actor.setFirstName("Tomek");
        actor.setLastName("Kowalski");
        actor.setAge(12);

        ActorDTO actorDTO = (ActorDTO) utils.mapperFromEntityToDto(actor);

        assertEquals(actor.getFirstName(), actorDTO.getFirstName());
        assertEquals(actor.getLastName(), actorDTO.getLastName());
        assertEquals(actor.getAge(), actorDTO.getAge());
    }

    @Test(expected = IllegalAccessException.class)
    public void should_throw_Illegal_argument_exception_during_parsing_fromEntity_To_DTO() throws InvocationTargetException, IllegalAccessException {
        Object o = new Object();
        utils.mapperFromEntityToDto(o);
    }

}
