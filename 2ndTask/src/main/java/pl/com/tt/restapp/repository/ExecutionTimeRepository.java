package pl.com.tt.restapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.tt.restapp.domain.ExecutionTime;

@Repository
public interface ExecutionTimeRepository extends JpaRepository<ExecutionTime, Long> {

    ExecutionTime getExecutionTimeByExecutionTimeId(Long id);
}
