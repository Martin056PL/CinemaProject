package pl.com.tt.restapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    
    List<Actor> findAll();

    Page<Actor> findAll(Specification<Actor> specification, Pageable pageable);

    Actor findAllByActorId(Long id);

    Page<Actor> findAllByMovie(Movie movie, Pageable pageable);

    Actor findActorByMovieAndActorId(Movie movie, Long actorId);

    @Transactional
    @Modifying
    @Query("delete from Actor a where a.actorId =?1")
    int deleteActorsByActorId(Long actorId);


}
