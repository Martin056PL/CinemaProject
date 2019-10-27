package pl.com.tt.restapp.report;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

import static pl.com.tt.restapp.report.utils.WriteFile.saveWorkbookToFile;


@RequiredArgsConstructor
@Configuration
public class ReportGenerator {

    private final MovieReport movieReport;
    private final ActorReport actorReport;
    private final StatisticReport statisticReport;

    public File createGeneralReport() throws IOException {

        Workbook workbook = new XSSFWorkbook();
        movieReport.createMovieActorReport(workbook);
        actorReport.createActorMovieReport(workbook);
        statisticReport.createStatisticReport(workbook);
        return saveWorkbookToFile(workbook);

    }
}
