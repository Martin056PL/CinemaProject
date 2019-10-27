package pl.com.tt.restapp.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.repository.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class MoviesInsertsTest {

    @Autowired
    private MovieRepository repository;

    private List<Movie> movieList;

    @Before
    public void getAllMovies() {
        movieList = repository.findAll();
    }

    @Test
    public void should_size_of_data_base_be_equal_to_size_of_csv() {
        assertEquals(100, movieList.size());
    }

    @Test
    public void should_quantity_of_action_movies_be_equals_6_with_default_configuration() {
        assertEquals(6, getActionMovies().size());
    }

    @Test
    public void should_quantity_of_movies_with_2013_year_premiere_be_equals_6_with_default_configuration(){
        assertEquals(2, getMoviesWith2013YearPremiere().size());
    }

    private List<Movie> getActionMovies() {
        return movieList.stream().filter(m -> m.getType().equals("ACTION")).collect(Collectors.toList());
    }

    private List<Movie> getMoviesWith2013YearPremiere(){
        return movieList.stream().filter(m -> m.getDatePremiere().getYear() == 2013).collect(Collectors.toList());
    }
}
