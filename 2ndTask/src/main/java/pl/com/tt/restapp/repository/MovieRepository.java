package pl.com.tt.restapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.com.tt.restapp.domain.Movie;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {

    List<Movie> findAll();

    Page<Movie> findAll(Pageable pageable);

    Page<Movie> findAll(Specification<Movie> specification, Pageable pageable);

    Movie findAllByMovieId(Long id);

    Boolean existsByMovieId(Long id);

}