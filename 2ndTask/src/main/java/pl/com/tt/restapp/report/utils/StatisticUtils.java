package pl.com.tt.restapp.report.utils;

import lombok.NoArgsConstructor;
import pl.com.tt.restapp.domain.Movie;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class StatisticUtils {

    static int sumOfMovies(List<Movie> listOfMovies) {
        return listOfMovies.size();
    }

    static int sumOfActors(List<Movie> listOfMovies) { return listOfMovies.stream().mapToInt(movie -> movie.getActors().size()).sum(); }

    static Double averageOfActorsPerMovie(Double actors, Double movies) {
        return (double) Math.round((actors / movies)*100)/100;
    }

    static Double averageOfMoviePerActor(Double actors, Double movies) {
        return (double) Math.round((movies / actors)*100)/100;
    }


}
