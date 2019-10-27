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
public class StatisticReport {

    private final Cells cells;

    void createStatisticReport(Workbook workbook) {

        Sheet sheet = workbook.createSheet("Statistic");

        Font headerFont = getHeaderFont(workbook);
        CellStyle headerCellStyle = getHeaderCellStyle(workbook, headerFont);
        CellStyle dataMiddleCellStyle = getContentCellStyle(workbook);
        cells.createHeadersForStatisticReport(sheet, headerCellStyle);
        cells.fillCellsInStatisticReport(sheet, dataMiddleCellStyle);
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(3);
    }
}
