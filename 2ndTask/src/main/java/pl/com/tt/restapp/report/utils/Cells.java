package pl.com.tt.restapp.report.utils;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.service.ActorService;
import pl.com.tt.restapp.service.MovieService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.util.CellUtil.createCell;

@RequiredArgsConstructor
@Component
public final class Cells {

    private final MovieService movieService;
    private final ActorService actorService;

    private final String[] genres = {"ACTION", "SCIFI", "HORROR", "ROMANS", "DRAMA", "ANIMATION", "THRILLER",
            "WESTERN", "BIOGRAPHY", "EROTIC", "CATASTROPHIC", "COMEDY", "ADVENTURE", "MORAL",
            "MUSICAL", "DOCUMENT", "FAMILY", "FANTASY", "GORE", "CRIMINAL", "PSYCHOLOGICAL",
            "ROMANTIC"};

    private final String[] statsParams = {
            "Total quantity of movies"
            , "Total quantity of actors"
            , "Average actors per movie"
            , "Average movie per actor"};

    public void createHeadersForEntitiesReport(Sheet sheet, String[] columns, CellStyle headerCellStyle) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            createCell(headerRow, i, columns[i], headerCellStyle);
        }
    }

    public void createHeadersForStatisticReport(Sheet sheet, CellStyle categoriesStyle) {
        for (int i = 0; i < genres.length; i++) {
            Row categoryRow = sheet.createRow(i);
            if (i < 4) {
                createCell(categoryRow, 0, statsParams[i], categoriesStyle);
            }
            createCell(categoryRow, 3, genres[i], categoriesStyle);
        }
    }

    public void createHeader(Sheet sheet, String[] columns, CellStyle headerCellStyle) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
    }

    public void fillCellsInStatisticReport(Sheet sheet, CellStyle dataStyle) {
        List<Movie> movieList = movieService.findAllMovies();

        for (int i = 0; i < genres.length; i++) {
            Row row = sheet.getRow(i);
            int sumOfMovies = StatisticUtils.sumOfMovies(movieList);
            int sumOfActors = StatisticUtils.sumOfActors(movieList);
            switch (i){
                case 0:
                    createCell(row, 1, String.valueOf(sumOfMovies), dataStyle);
                    createCell(row, 4, countAllGenres().get(genres[i]).toString(), dataStyle);
                    break;
                case 1:
                    createCell(row, 1, String.valueOf(sumOfActors), dataStyle);
                    createCell(row, 4, countAllGenres().get(genres[i]).toString(), dataStyle);
                    break;
                case 2:
                    createCell(row, 1, String.valueOf(StatisticUtils.averageOfActorsPerMovie((double) sumOfActors, (double) sumOfMovies)), dataStyle);
                    createCell(row, 4, countAllGenres().get(genres[i]).toString(), dataStyle);
                    break;
                case 3:
                    createCell(row, 1, String.valueOf(StatisticUtils.averageOfMoviePerActor((double) sumOfActors, (double) sumOfMovies)), dataStyle);
                    createCell(row, 4, countAllGenres().get(genres[i]).toString(), dataStyle);
                    break;
                default:
                    createCell(row, 4, countAllGenres().get(genres[i]).toString(), dataStyle);
                    break;
            }
        }
    }

    public void fillSheetByMovieActorData(Sheet sheet, CellStyle cellStyle) {
        Row row;
        List<Movie> listOfMovies = movieService.findAllMovies();
        int counter = 0;
        for (Movie movieElement : listOfMovies) {
            List<Actor> actorsList = movieElement.getActors();
            int actorsInMovieElement = movieElement.getActors().size();
                for (Actor actorElement : actorsList) {
                    counter++;
                    row = sheet.createRow(counter);
                    createCell(row, 0, movieElement.getTitle(), cellStyle);
                    createCell(row, 1, movieElement.getType(), cellStyle);
                    createCell(row, 2, movieElement.getDatePremiere().toString(), cellStyle);
                    createCell(row, 3, actorElement.getFirstName(), cellStyle);
                    createCell(row, 4, actorElement.getLastName(), cellStyle);
                    createCell(row, 5, actorElement.getAge().toString(), cellStyle);
                }
                if (actorsInMovieElement >= 2) {
                sheet.addMergedRegion(new CellRangeAddress(counter - actorsInMovieElement + 1, counter, 0, 0));
                sheet.addMergedRegion(new CellRangeAddress(counter - actorsInMovieElement + 1, counter, 1, 1));
                sheet.addMergedRegion(new CellRangeAddress(counter - actorsInMovieElement + 1, counter, 2, 2));
            }
        }
    }

    public void fillSheetByData(Sheet sheet, int start, int end, CellStyle cellStyle) {
        List<Actor> listOfActors = actorService.findAllActors();

        for (int i = start; i < end; i++) {
            Row row = sheet.createRow(i);
            Actor actor = listOfActors.get(i - 1);
            Movie actorsMovie = actor.getMovie().get(0);

            createCell(row, 0, actor.getFirstName(), cellStyle);
            createCell(row, 1, actor.getLastName(), cellStyle);
            createCell(row, 2, actor.getAge().toString(), cellStyle);
            createCell(row, 3, actorsMovie.getTitle(), cellStyle);
            createCell(row, 4, actorsMovie.getType(), cellStyle);
            createCell(row, 5, actorsMovie.getDatePremiere().toString(), cellStyle);
        }
    }

    private Map<String, Integer> countAllGenres() {
        List<Movie> listOfMovies = movieService.findAllMovies();
        Map<String, Integer> genreCounter = new HashMap<>();
        for (String genre : genres) {
            genreCounter.put(genre, 0);
        }
        for (Movie movie : listOfMovies) {
            for (String genre : genres) {
                if (movie.getType().equals(genre)) {
                    genreCounter.replace(movie.getType(), genreCounter.get(genre) + 1);
                }
            }
        }
        return genreCounter;
    }
}
