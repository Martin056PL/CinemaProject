package pl.com.tt.restapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.com.tt.restapp.service.ReportService;

import java.io.IOException;

@RequestMapping("report")
@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity generateReport() throws IOException {
        reportService.generateReportAndSaveToDB();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reportId}")
    public ResponseEntity takeReport(@PathVariable Long reportId) throws IOException {
        reportService.getReportById(reportId);
        return ResponseEntity.ok().build();
    }
}
