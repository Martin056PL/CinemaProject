package pl.com.tt.restapp.service;

import pl.com.tt.restapp.domain.ExecutionTime;

import java.util.List;

public interface ExecutionTimeService {
    List<ExecutionTime> getAllExecutionTimes();

    ExecutionTime getExecutionTimeByExecutionTimeId(Long executionTimeId);

    void saveExecutionTimeToDataBase(ExecutionTime executionTime);
}
