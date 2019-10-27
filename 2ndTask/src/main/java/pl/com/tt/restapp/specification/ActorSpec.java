package pl.com.tt.restapp.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActorSpec {

    public static Specification<Actor> query(final Long movieId, String name, Integer age) {
        return (root, query, cb) -> {
            Predicate predicateMovieID = getPredicateByMovieId(movieId, root, query, cb);
            Optional<Integer> optionalActorAge = Optional.ofNullable(age);

            Boolean nameParameterIncludeInRequest = Strings.isNotBlank(name);
            Boolean ageParameterIncludeInRequest = optionalActorAge.isPresent();

            String patternName = "%" + name + "%";

            if (nameParameterIncludeInRequest && optionalActorAge.isPresent()) {
                Predicate movieIdAndFirstNameWithAge = cb.and(predicateMovieID, cb.like(root.get("firstName"), patternName), cb.equal(root.get("age"), age));
                Predicate movieIdAndLastNameWithAge = cb.and(predicateMovieID, cb.like(root.get("lastName"), patternName), cb.equal(root.get("age"), age));
                return cb.or(movieIdAndFirstNameWithAge, movieIdAndLastNameWithAge);
            } else if (nameParameterIncludeInRequest) {
                Predicate movieIdAndFirstName = cb.and(predicateMovieID, cb.like(root.get("firstName"), patternName));
                Predicate movieIdAndLastName = cb.and(predicateMovieID, cb.like(root.get("lastName"), patternName));
                return cb.or(movieIdAndFirstName, movieIdAndLastName);
            } else if (ageParameterIncludeInRequest) {
                Predicate movieIdAndAge = cb.and(predicateMovieID, cb.equal(root.get("age"), age));
                return cb.or(movieIdAndAge);
            }
            return cb.and(predicateMovieID);
        };
    }

    private static Predicate getPredicateByMovieId(Long movieId, Root<Actor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        query.distinct(true);
        Subquery<Movie> movieSubQuery = query.subquery(Movie.class);
        Root<Movie> movie = movieSubQuery.from(Movie.class);
        Expression<List<Actor>> actors = movie.get("actors");
        movieSubQuery.select(movie);
        movieSubQuery.where(cb.equal(movie.get("movieId"), movieId), cb.isMember(root, actors));
        return cb.exists(movieSubQuery);
    }
}