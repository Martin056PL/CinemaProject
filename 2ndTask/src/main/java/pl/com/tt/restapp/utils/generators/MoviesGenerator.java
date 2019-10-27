package pl.com.tt.restapp.utils.generators;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.repository.MovieRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MoviesGenerator {

    private final MovieRepository repository;

   @EventListener(ApplicationReadyEvent.class)
    public void generateMovie() {

        for (int i = 0; i < 100; i++) {

            Movie movie = new Movie();
            movie.setTitle(randomMovieTitle());
            movie.setType(MovieType.valueOf(randomMovieType()).toString());
            movie.setDatePremiere(createRandomDate());

            List<Actor> actorList = new ArrayList<>();

            for (int j = 0; j <= RandomUtils.nextInt(0, 4); j++) {
                Actor actor = new Actor();
                actor.setFirstName(randomFirstName());
                actor.setLastName(randomLastName());
                actor.setAge(RandomUtils.nextInt(18, 81));
                actorList.add(actor);
            }
            movie.setActors(actorList);
            repository.save(movie);
            actorList.clear();
        }
    }

    private static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private static LocalDate createRandomDate() {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(1950, 2019);
        return LocalDate.of(year, month, day);
    }

    private static int randomMovieType() {
        return RandomUtils.nextInt(1, 23);
    }

    private static String randomFirstName() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    private static String randomLastName() {
        Faker faker = new Faker();
        return faker.name().lastName();
    }

    private static String randomMovieTitle() {
        Faker faker = new Faker();
        return faker.lorem().sentence();
    }


}
