package pl.com.tt.restapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.tt.restapp.domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

}
