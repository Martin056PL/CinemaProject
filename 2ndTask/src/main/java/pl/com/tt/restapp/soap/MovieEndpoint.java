package pl.com.tt.restapp.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.repository.MovieRepository;
import pl.com.tt.restapp.service.MovieService;
import pl.com.tt.restapp.soap.sources.*;
import pl.com.tt.restapp.utils.date.XmlDateMapper;

import javax.jws.WebService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebService(endpointInterface = "pl.com.tt.restapp.soap.sources.MoviesPort")
public class MovieEndpoint implements MoviesPort {


    @Autowired
    private MovieRepository repository;
    @Autowired
    private MovieService service;

    private static final long ERROR_ID_VALUE = -9999L;
    private static final String ERROR_OBJECT_CHECK_STATUS_CODE = "Invalid object! Check status code!";
    private static final String HASN_T_BEEN_FOUND_MESSAGE = "Your movie hasn't been found!";

    @Override
    public GetAllMoviesResponse getAllMovies(GetAllMoviesRequest getAllMoviesRequest) {
        GetAllMoviesResponse response = new GetAllMoviesResponse();
        List<Movie> entityMovieList = service.findAllMovies();
        List<MovieSOAP> listOfSoapMovie = new ArrayList<>();

        for (Movie movie : entityMovieList) {
            listOfSoapMovie.add(converterMovieEntityToMovieSOAP(movie));
        }

        if (listOfSoapMovie.isEmpty()) {
            ServiceStatus serviceStatus = returnServiceStatus(HttpStatus.NO_CONTENT, "List is empty!");
            response.setServiceStatus(serviceStatus);
            listOfSoapMovie.add(returnEmptyMovie());
            response.getMovieSOAP().addAll(listOfSoapMovie);
            return response;
        } else {
            ServiceStatus serviceStatus = returnServiceStatus(HttpStatus.OK, "Your movies:");
            response.getMovieSOAP().addAll(listOfSoapMovie);
            response.setServiceStatus(serviceStatus);
            return response;
        }
    }

    @Override
    public GetMoviesByIdResponse getMoviesById(GetMoviesByIdRequest request) {
        GetMoviesByIdResponse response = new GetMoviesByIdResponse();
        Long movieId = request.getMovieId();
        Optional<Movie> optionalMovie = service.findMovieById(movieId);
        if (optionalMovie.isPresent()) {
            ServiceStatus serviceStatus = returnServiceStatus(HttpStatus.OK, "Your movie:");
            response.setMovieSOAP(converterMovieEntityToMovieSOAP(optionalMovie.get()));
            response.setServiceStatus(serviceStatus);
            return response;
        } else {
            ServiceStatus serviceStatus = returnServiceStatus(HttpStatus.NOT_FOUND, HASN_T_BEEN_FOUND_MESSAGE);
            response.setServiceStatus(serviceStatus);
            response.setMovieSOAP(returnEmptyMovie());
            return response;
        }
    }

    @Override
    public CreateMovieResponse createMovie(CreateMovieRequest request) {
        CreateMovieResponse response = new CreateMovieResponse();
        Movie movie = converterRequestMovieToMovieEntity(request.getRequestBodySOAPMovie());
        Movie result = service.saveMovie(movie);
        ServiceStatus serviceStatus = returnServiceStatus(HttpStatus.CREATED, "Created!");
        response.setServiceStatus(serviceStatus);
        response.setMovieSOAP(converterMovieEntityToMovieSOAP(result));
        return response;
    }

    @Override
    public UpdateMovieResponse updateMovie(UpdateMovieRequest request) {
        UpdateMovieResponse response = new UpdateMovieResponse();
        Long movieId = request.getId();
        Optional<Movie> optionalMovie = service.findMovieById(movieId);
        if (optionalMovie.isEmpty()) {
            ServiceStatus serviceStatus = returnServiceStatus(HttpStatus.NOT_FOUND, HASN_T_BEEN_FOUND_MESSAGE);
            response.setServiceStatus(serviceStatus);
            response.setMovieSOAP(returnEmptyMovie());
            return response;
        } else {
            Movie movieFromDB = optionalMovie.get();
            Movie movieFromRequest = converterRequestMovieToMovieEntity(request.getUpdateMovie());
            Movie updatedMovie = copyParams(movieFromRequest, movieFromDB);
            Movie result = service.saveMovie(updatedMovie);
            ServiceStatus serviceStatus = returnServiceStatus(HttpStatus.OK, "Updated!");
            response.setMovieSOAP(converterMovieEntityToMovieSOAP(result));
            response.setServiceStatus(serviceStatus);
            return response;
        }
    }

    @Override
    public DeleteMovieResponse deleteMovie(DeleteMovieRequest request) {
        DeleteMovieResponse response = new DeleteMovieResponse();
        Long movieId = request.getId();
        Optional<Movie> optionalMovie = service.findMovieById(movieId);
        if (optionalMovie.isEmpty()) {
            ServiceStatus serviceStatus = returnServiceStatus(HttpStatus.NOT_FOUND, HASN_T_BEEN_FOUND_MESSAGE);
            response.setServiceStatus(serviceStatus);
            return response;
        } else {
            service.deleteMovieById(movieId);
            ServiceStatus serviceStatus = returnServiceStatus(HttpStatus.NO_CONTENT, "Deleted!");
            response.setServiceStatus(serviceStatus);
            return response;
        }
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

    private ServiceStatus returnServiceStatus(HttpStatus httpStatus, String message) {
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode(httpStatus.toString());
        serviceStatus.setMessage(message);
        return serviceStatus;
    }

    private Movie copyParams(Movie sourceMovie, Movie targetMovie) {
        targetMovie.setTitle(sourceMovie.getTitle());
        targetMovie.setType(sourceMovie.getType());
        targetMovie.setDatePremiere(sourceMovie.getDatePremiere());
        return targetMovie;
    }

    private MovieSOAP returnEmptyMovie() {
        MovieSOAP emptyMovieSOAP = new MovieSOAP();
        emptyMovieSOAP.setMovieId(ERROR_ID_VALUE);
        emptyMovieSOAP.setTitle(ERROR_OBJECT_CHECK_STATUS_CODE);
        emptyMovieSOAP.setType(ERROR_OBJECT_CHECK_STATUS_CODE);
        emptyMovieSOAP.setDatePremiere(XmlDateMapper.localDateToXmlGregorianConverter(LocalDate.now()));
        return emptyMovieSOAP;
    }
}
