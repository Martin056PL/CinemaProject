package pl.com.tt.restapp.feignclient.controllerfeignclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pl.com.tt.restapp.configutarion.TaskFeignClientConfiguration;
import pl.com.tt.restapp.feignclient.domainfeignclient.TaskFeign;

import java.util.List;

@FeignClient(value = "api", url = "localhost:12345/tasks", configuration = TaskFeignClientConfiguration.class)
public interface TaskFeignClient {

    @GetMapping
    List<TaskFeign> getAllTasks();

    @GetMapping(value = "{id}")
    TaskFeign getProperTaskById(@PathVariable (name = "id") Long id);

    @PostMapping(consumes = "application/json")
    TaskFeign addNewTask(@RequestBody TaskFeign taskFeign);

    @PutMapping(value = "{id}", consumes = "application/json", produces = "application/json")
    TaskFeign updateTask(@PathVariable (name = "id") Long id, @RequestBody TaskFeign taskFeign);

    @DeleteMapping(value = "{id}")
    void deleteTask(@PathVariable (name = "id") Long id);
}
