package pl.com.tt.restapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.tt.restapp.domain.ExecutionTime;
import pl.com.tt.restapp.repository.ExecutionTimeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExecutionTimeServiceImpl implements ExecutionTimeService {

    private final ExecutionTimeRepository repository;

    @Override
    public List<ExecutionTime> getAllExecutionTimes(){
        return repository.findAll();
    }

    @Override
    public ExecutionTime getExecutionTimeByExecutionTimeId(Long executionTimeId){
        return repository.getExecutionTimeByExecutionTimeId(executionTimeId);
    }

    @Override
    public void saveExecutionTimeToDataBase(ExecutionTime executionTime){
        repository.save(executionTime);
    }
}
