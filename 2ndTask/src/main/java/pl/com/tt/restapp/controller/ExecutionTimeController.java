package pl.com.tt.restapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.com.tt.restapp.domain.ExecutionTime;
import pl.com.tt.restapp.service.ExecutionTimeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ExecutionTimeController {

    private final ExecutionTimeService service;


    @GetMapping("executionTime")
    public ResponseEntity<List<ExecutionTime>> getAllExecutionTimes() {
        return ResponseEntity.ok().body(service.getAllExecutionTimes());
    }

    @GetMapping("executionTime/{executionTimeId}")
    public ResponseEntity<ExecutionTime> getExecutionTimeByExecutionTimeId(@PathVariable Long executionTimeId){
        return ResponseEntity.ok().body(service.getExecutionTimeByExecutionTimeId(executionTimeId));
    }
}
