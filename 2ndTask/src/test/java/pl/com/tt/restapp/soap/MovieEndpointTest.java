package pl.com.tt.restapp.soap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.service.MovieServiceImpl;
import pl.com.tt.restapp.soap.sources.*;
import pl.com.tt.restapp.utils.date.XmlDateMapper;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieEndpointTest {

    @Mock
    GetAllMoviesRequest getAllMoviesRequest;

    @Mock
    GetMoviesByIdRequest getMoviesByIdRequest;

    @Mock
    MovieServiceImpl service;

    @InjectMocks
    MovieEndpoint endpoint;

    private static final long ERROR_ID_VALUE = -9999L;
    private static final String ERROR_OBJECT_CHECK_STATUS_CODE = "Invalid object! Check status code!";
    private static final long MOVIE_ID = 1L;

    @Test
    public void should_return_error_object_when_controller_returlns_empty_list() {
        GetAllMoviesResponse response = new GetAllMoviesResponse();
        when(service.findAllMovies()).thenReturn(Collections.emptyList());
        List<MovieSOAP> list = new ArrayList<>();
        list.add(returnEmptyMovie());
        response.getMovieSOAP().addAll(list);
        response.setServiceStatus(returnServiceStatus(HttpStatus.NO_CONTENT, "List is empty!"));

        assertEquals(response.getMovieSOAP().get(0).getDatePremiere(), endpoint.getAllMovies(getAllMoviesRequest).getMovieSOAP().get(0).getDatePremiere());
        assertEquals(response.getMovieSOAP().get(0).getMovieId(), endpoint.getAllMovies(getAllMoviesRequest).getMovieSOAP().get(0).getMovieId());
        assertEquals(response.getMovieSOAP().get(0).getTitle(), endpoint.getAllMovies(getAllMoviesRequest).getMovieSOAP().get(0).getTitle());
        assertEquals(response.getMovieSOAP().get(0).getType(), endpoint.getAllMovies(getAllMoviesRequest).getMovieSOAP().get(0).getType());
        assertEquals(response.getServiceStatus().getMessage(), endpoint.getAllMovies(getAllMoviesRequest).getServiceStatus().getMessage());
        assertEquals(response.getServiceStatus().getStatusCode(), endpoint.getAllMovies(getAllMoviesRequest).getServiceStatus().getStatusCode());
    }

    @Test
    public void should_status_code_be_ok_and_body_response_be_equal_to_controller_response() {

        GetAllMoviesResponse response = new GetAllMoviesResponse();
        response.setServiceStatus(returnServiceStatus(HttpStatus.OK, "Your movies:"));
        response.getMovieSOAP().add(converterMovieEntityToMovieSOAP(returnTestMovieObject()));

        when(service.findAllMovies()).thenReturn(Collections.singletonList(returnTestMovieObject()));

        assertEquals(response.getServiceStatus().getMessage(), endpoint.getAllMovies(getAllMoviesRequest).getServiceStatus().getMessage());
        assertEquals(response.getServiceStatus().getStatusCode(), endpoint.getAllMovies(getAllMoviesRequest).getServiceStatus().getStatusCode());
        assertEquals(response.getMovieSOAP().size(), endpoint.getAllMovies(getAllMoviesRequest).getMovieSOAP().size());
        assertEquals(response.getMovieSOAP().get(0).getDatePremiere(), endpoint.getAllMovies(getAllMoviesRequest).getMovieSOAP().get(0).getDatePremiere());
        assertEquals(response.getMovieSOAP().get(0).getMovieId(), endpoint.getAllMovies(getAllMoviesRequest).getMovieSOAP().get(0).getMovieId());
        assertEquals(response.getMovieSOAP().get(0).getTitle(), endpoint.getAllMovies(getAllMoviesRequest).getMovieSOAP().get(0).getTitle());
        assertEquals(response.getMovieSOAP().get(0).getType(), endpoint.getAllMovies(getAllMoviesRequest).getMovieSOAP().get(0).getType());
    }

    @Test
    public void should_status_code_be_NOT_FOUND_when_when_controller_do_not_find_movie_base_on_id() {
        GetMoviesByIdResponse response = new GetMoviesByIdResponse();
        response.setServiceStatus(returnServiceStatus(HttpStatus.NOT_FOUND, "Your movie hasn't been found!"));
        response.setMovieSOAP(returnEmptyMovie());
        when(getMoviesByIdRequest.getMovieId()).thenReturn(MOVIE_ID);
        when(service.findMovieById(MOVIE_ID)).thenReturn(Optional.empty());


        assertEquals(response.getMovieSOAP().getDatePremiere(), endpoint.getMoviesById(getMoviesByIdRequest).getMovieSOAP().getDatePremiere());
        assertEquals(response.getMovieSOAP().getMovieId(), endpoint.getMoviesById(getMoviesByIdRequest).getMovieSOAP().getMovieId());
        assertEquals(response.getMovieSOAP().getTitle(), endpoint.getMoviesById(getMoviesByIdRequest).getMovieSOAP().getTitle());
        assertEquals(response.getMovieSOAP().getType(), endpoint.getMoviesById(getMoviesByIdRequest).getMovieSOAP().getType());
        assertEquals(response.getServiceStatus().getMessage(), endpoint.getMoviesById(getMoviesByIdRequest).getServiceStatus().getMessage());
        assertEquals(response.getServiceStatus().getStatusCode(), endpoint.getMoviesById(getMoviesByIdRequest).getServiceStatus().getStatusCode());
    }

    @Test
    public void should_status_code_bo_ok_when_controller_find_movie_base_on_id() {
        GetMoviesByIdResponse response = new GetMoviesByIdResponse();
        response.setServiceStatus(returnServiceStatus(HttpStatus.OK, "Your movie:"));
        response.setMovieSOAP(converterMovieEntityToMovieSOAP(returnTestMovieObject()));
        when(getMoviesByIdRequest.getMovieId()).thenReturn(MOVIE_ID);
        when(service.findMovieById(MOVIE_ID)).thenReturn(Optional.of(returnTestMovieObject()));

        assertEquals(response.getMovieSOAP().getDatePremiere(), endpoint.getMoviesById(getMoviesByIdRequest).getMovieSOAP().getDatePremiere());
        assertEquals(response.getMovieSOAP().getMovieId(), endpoint.getMoviesById(getMoviesByIdRequest).getMovieSOAP().getMovieId());
        assertEquals(response.getMovieSOAP().getTitle(), endpoint.getMoviesById(getMoviesByIdRequest).getMovieSOAP().getTitle());
        assertEquals(response.getMovieSOAP().getType(), endpoint.getMoviesById(getMoviesByIdRequest).getMovieSOAP().getType());
        assertEquals(response.getServiceStatus().getMessage(), endpoint.getMoviesById(getMoviesByIdRequest).getServiceStatus().getMessage());
        assertEquals(response.getServiceStatus().getStatusCode(), endpoint.getMoviesById(getMoviesByIdRequest).getServiceStatus().getStatusCode());
    }

    private ServiceStatus returnServiceStatus(HttpStatus httpStatus, String message) {
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(httpStatus.toString());
        serviceStatus.setMessage(message);
        return serviceStatus;
    }

    private MovieSOAP returnEmptyMovie() {
        MovieSOAP emptyMovieSOAP = new MovieSOAP();
        emptyMovieSOAP.setMovieId(ERROR_ID_VALUE);
        emptyMovieSOAP.setTitle(ERROR_OBJECT_CHECK_STATUS_CODE);
        emptyMovieSOAP.setType(ERROR_OBJECT_CHECK_STATUS_CODE);
        emptyMovieSOAP.setDatePremiere(XmlDateMapper.localDateToXmlGregorianConverter(LocalDate.now()));
        return emptyMovieSOAP;
    }

    private Movie returnTestMovieObject() {
        Movie movie = new Movie();
        movie.setTitle("title");
        movie.setType("type");
        movie.setDatePremiere(LocalDate.parse("2000-12-12"));
        movie.setMovieId(1L);
        return movie;
    }

    private MovieSOAP converterMovieEntityToMovieSOAP(Movie entityMovie) {
        MovieSOAP movieSOAP = new MovieSOAP();
        movieSOAP.setMovieId(entityMovie.getMovieId());
        movieSOAP.setTitle(entityMovie.getTitle());
        movieSOAP.setType(entityMovie.getType());
        movieSOAP.setDatePremiere(XmlDateMapper.localDateToXmlGregorianConverter(entityMovie.getDatePremiere()));
        return movieSOAP;
    }

    private Movie converterRequestMovieToMovieEntity(CreateAndUpdateMovieRequest requestBodyMovie) {
        Movie movie = new Movie();
        movie.setTitle(requestBodyMovie.getTitle());
        movie.setType(requestBodyMovie.getType());
        movie.setDatePremiere(XmlDateMapper.xmlGregorianToLocalDateConverter(requestBodyMovie.getDatePremiere()));
        return movie;
    }

    private XMLGregorianCalendar returnXmlDate() throws DatatypeConfigurationException {
        XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        date.setDay(1);
        date.setMonth(1);
        date.setYear(2000);
        return date;
    }


}
