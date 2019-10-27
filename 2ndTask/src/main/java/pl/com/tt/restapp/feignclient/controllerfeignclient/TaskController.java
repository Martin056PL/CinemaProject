package pl.com.tt.restapp.feignclient.controllerfeignclient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.tt.restapp.exceptionservice.customexceptions.CustomFeignException;
import pl.com.tt.restapp.exceptionservice.errormessages.FeignExceptionsErrorMessages;
import pl.com.tt.restapp.feignclient.domainfeignclient.TaskFeign;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/tasks")
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class TaskController {

    private final TaskFeignClient taskFeignClientInterface;

    @GetMapping
    public ResponseEntity<List<TaskFeign>> getAllTasks() {
        List<TaskFeign> listOfAllTasks = taskFeignClientInterface.getAllTasks();
        log.debug("Endpoint address: 'tasks' with GET method");
        if (listOfAllTasks.isEmpty()) {
            log.debug("Application returned empty list");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            log.debug("Application returned list of all tasks with id: {}", Arrays.toString(listOfAllTasks.stream().map(TaskFeign::getId).toArray()));
            return ResponseEntity.ok().body(listOfAllTasks);
        }
    }

    @GetMapping(value = "{id}")
    public ResponseEntity getProperTaskById(@PathVariable Long id) {
        try {
            log.debug("Endpoint address: 'tasks/{id}' with GET method\nrequests params: id = {}", id);
            return ResponseEntity.ok(taskFeignClientInterface.getProperTaskById(id));
        } catch (FeignException ex) {
            log.debug("User didn't found task base on delivered id = {}", id);
            throw new CustomFeignException(ex.status(), FeignExceptionsErrorMessages.ERROR_GET_MESSAGE.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity addNewTask(@RequestBody TaskFeign taskFeign) {
        log.debug("Endpoint address: 'tasks' with POST method\nrequest body: {}", taskFeign);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(taskFeignClientInterface.addNewTask(taskFeign));
        } catch (FeignException ex) {
            log.debug("Task didn't add successfully");
            throw new CustomFeignException(ex.status(), FeignExceptionsErrorMessages.ERROR_POST_MESSAGE.getMessage());
        }
    }

    @PutMapping(value = "{id}")
    public ResponseEntity updateTask(@PathVariable Long id, @RequestBody TaskFeign taskFeign) {
        log.debug("Endpoint address: 'task/id' with PUT method\nrequests params: id = {}", id);
        log.debug("Request body: {}", taskFeign);
        try {
            return ResponseEntity.ok().body(taskFeignClientInterface.updateTask(id, taskFeign));
        } catch (FeignException ex) {
            log.debug("Task didn't update successfully");
            throw new CustomFeignException(ex.status(), FeignExceptionsErrorMessages.ERROR_PUT_MESSAGE.getMessage());
        }
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {
        log.debug("Endpoint address: 'tasks/id' with DELETE method\nrequests params: movieId = {}", id);
        try {
            taskFeignClientInterface.deleteTask(id);
        } catch (FeignException ex) {
            log.debug("Task didn't delete successfully");
            throw new CustomFeignException(ex.status(), FeignExceptionsErrorMessages.ERROR_DELETE_MESSAGE.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
