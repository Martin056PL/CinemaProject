package pl.com.tt.restapp.report;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import pl.com.tt.restapp.report.utils.Cells;
import pl.com.tt.restapp.service.ActorService;

import static pl.com.tt.restapp.report.utils.Styles.*;

@RequiredArgsConstructor
@Component
public class ActorReport {

    private static final String ACTOR_MOVIES = "Actor-Movies";
    private final ActorService service;
    private final Cells cells;


    void createActorMovieReport(Workbook workbook) {

        String[] columns = {"First Name", "Last Name", "Age", "Title", "Genre", "Data Premiere"};

        Sheet sheet = workbook.createSheet(ACTOR_MOVIES);

        Font headerFont = getHeaderFont(workbook);
        CellStyle headerCellStyle = getHeaderCellStyle(workbook, headerFont);
        cells.createHeader(sheet, columns, headerCellStyle);

        CellStyle dataMiddleCellStyle = getContentCellStyle(workbook);
        cells.fillSheetByData(sheet, 1, service.findAllActors().size(), dataMiddleCellStyle);
        AutoSizeColumns(columns, sheet);
    }


}
