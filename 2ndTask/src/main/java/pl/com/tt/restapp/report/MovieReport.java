package pl.com.tt.restapp.report;


import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import pl.com.tt.restapp.report.utils.Cells;

import static pl.com.tt.restapp.report.utils.Styles.*;

@RequiredArgsConstructor
@Component
public class MovieReport {

    private final Cells cells;

    void createMovieActorReport(Workbook workbook) {
        String[] columns = {"Title", "Genre", "Data Premiere", "First Name", "Last Name", "Age"};
        Sheet sheet = workbook.createSheet("Movie-Actor");

        Font headerFont = getHeaderFont(workbook);
        CellStyle headerCellStyle = getHeaderCellStyle(workbook, headerFont);
        cells.createHeadersForEntitiesReport(sheet, columns, headerCellStyle);

        CellStyle dataMiddleCellStyle = getContentCellStyle(workbook);
        cells.fillSheetByMovieActorData(sheet, dataMiddleCellStyle);
        AutoSizeColumns(columns, sheet);
    }


}
