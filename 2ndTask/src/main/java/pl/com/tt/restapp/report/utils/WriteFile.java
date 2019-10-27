package pl.com.tt.restapp.report.utils;

import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public final class WriteFile {

    public static File saveWorkbookToFile(Workbook workbook) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        File currDir = new File("./src/main/resources/reports/Report_" + now.format(formatter) + ".xlsx");
        String path = currDir.getAbsolutePath();
        FileOutputStream outputStream = new FileOutputStream(path);
        workbook.write(outputStream);
        workbook.close();
        return currDir;
    }
}
