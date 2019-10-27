package pl.com.tt.restapp.service;

import org.springframework.web.bind.annotation.PathVariable;
import pl.com.tt.restapp.domain.Report;

import java.io.IOException;

public interface ReportService {

    void generateReportAndSaveToDB() throws IOException;

    Report getReportById(@PathVariable Long reportId) throws IOException;
}
