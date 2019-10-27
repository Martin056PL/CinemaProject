package pl.com.tt.restapp.service;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.com.tt.restapp.domain.Report;
import pl.com.tt.restapp.report.ReportGenerator;
import pl.com.tt.restapp.repository.ReportRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Paths.get;
import static org.apache.commons.io.FileUtils.readFileToByteArray;
import static org.apache.commons.io.FileUtils.writeByteArrayToFile;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportGenerator generator;
    private final ReportRepository reportRepository;

    @Override
    @Scheduled(cron = "0 0 * * * *")
    public void generateReportAndSaveToDB() throws IOException {
        File file = generator.createGeneralReport();
        String location = file.getAbsolutePath();
        Path path = get(location);

        String name = path.getFileName().toString();
        byte[] content = readFileToByteArray(new File(location));

        Report report = new Report();
        report.setReport(content);
        report.setReportName(name);
        reportRepository.save(report);
    }

    @Override
    public Report getReportById(Long reportId) throws IOException {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(IllegalAccessError::new);

        String fileName = report.getReportName();
        byte[] reportFile = report.getReport();
        File file = new File("src/main/resources/download/" + fileName);
        writeByteArrayToFile(file, reportFile);

        return report;


    }
}
