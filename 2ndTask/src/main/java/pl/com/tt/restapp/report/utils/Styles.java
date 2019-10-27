package pl.com.tt.restapp.report.utils;

import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;

import static lombok.AccessLevel.PRIVATE;
import static org.apache.poi.hssf.record.cf.PatternFormatting.SOLID_FOREGROUND;
import static org.apache.poi.ss.usermodel.BorderStyle.THICK;
import static org.apache.poi.ss.usermodel.BorderStyle.THIN;
import static org.apache.poi.ss.usermodel.IndexedColors.*;
import static org.apache.poi.ss.usermodel.VerticalAlignment.CENTER;

@NoArgsConstructor(access = PRIVATE)
public final class Styles {

    public static Font getHeaderFont(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(RED.getIndex());
        return headerFont;
    }

    public static CellStyle getContentCellStyle(Workbook workbook) {
        CellStyle dataCellStyle = workbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setVerticalAlignment(CENTER);
        dataCellStyle.setBorderTop(THIN);
        dataCellStyle.setBorderBottom(THIN);
        dataCellStyle.setBorderLeft(THIN);
        dataCellStyle.setBorderRight(THIN);
        return dataCellStyle;
    }

    public static CellStyle getHeaderCellStyle(Workbook workbook, Font headerFont) {
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCellStyle.setFillForegroundColor(CORAL.getIndex());
        headerCellStyle.setFillPattern(SOLID_FOREGROUND);
        headerCellStyle.setBorderBottom(THICK);
        headerCellStyle.setBorderLeft(THICK);
        headerCellStyle.setBorderRight(THICK);
        headerCellStyle.setBorderTop(THICK);
        headerCellStyle.setTopBorderColor(BLACK.getIndex());
        return headerCellStyle;
    }

    public static void AutoSizeColumns(String[] columns, Sheet sheet) {
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }
}
