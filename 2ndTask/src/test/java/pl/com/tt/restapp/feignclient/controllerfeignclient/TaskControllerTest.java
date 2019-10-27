package pl.com.tt.restapp.feignclient.controllerfeignclient;

import feign.FeignException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.com.tt.restapp.feignclient.domainfeignclient.TaskFeign;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    @Mock
    TaskFeign taskFeign;

    @Mock
    List<TaskFeign> taskFeignsList;

    @Mock
    TaskFeignClient taskFeignClient;

    @Mock
    FeignException exception;

    @InjectMocks
    TaskController controller;

    private static final Long ID = 1L;

    @Test
    public void should_return_status_no_content_when_controller_will_return_empty_list() {
        when(taskFeignClient.getAllTasks()).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.NO_CONTENT, controller.getAllTasks().getStatusCode());
    }

    @Test
    public void should_return_status_ok_when_controller_will_return_not_empty_list() {
        when(taskFeignClient.getAllTasks()).thenReturn(taskFeignsList);
        assertEquals(HttpStatus.OK, controller.getAllTasks().getStatusCode());
    }

    @Test
    public void should_return_status_ok_when_controller_return_task_base_on_id() {
        when(taskFeignClient.getProperTaskById(ID)).thenReturn(taskFeign);
        assertEquals(HttpStatus.OK, controller.getProperTaskById(ID).getStatusCode());
    }

    @Test(expected = FeignException.class)
    public void should_throw_feign_exception_when_controller_returns_http_status_NOTFOUND_or_BADREQUEST_during_getTaskByID() {
        when(controller.getProperTaskById(ID)).thenThrow(exception);
        controller.getProperTaskById(ID);
    }

    @Test
    public void should_return_status_created_when_controller_successfully_add_new_task() {
        when(taskFeignClient.addNewTask(taskFeign)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(taskFeign).getBody());
        assertEquals(HttpStatus.CREATED, controller.addNewTask(taskFeign).getStatusCode());
    }

    @Test(expected = FeignException.class)
    public void should_throw_feign_exception_when_controller_returns_http_status_NOTFOUND_or_BADREQUEST_during_addTask() {
        when(controller.addNewTask(taskFeign)).thenThrow(exception);
        controller.addNewTask(taskFeign);
    }

    @Test
    public void should_return_status_ok_when_controller_successfully_update_task() {
        when(taskFeignClient.updateTask(ID, taskFeign)).thenReturn(ResponseEntity.ok().body(taskFeign).getBody());
        assertEquals(HttpStatus.OK, controller.updateTask(ID,taskFeign).getStatusCode());
    }

    @Test(expected = FeignException.class)
    public void should_throw_feign_exception_when_controller_returns_http_status_NOTFOUND_or_BADREQUEST_during_update_task() {
        when(controller.updateTask(ID, taskFeign)).thenThrow(exception);
        controller.updateTask(ID, taskFeign);
    }

    @Test
    public void should_return_status_ok_when_controller_successfully_delete_task() {
        assertEquals(HttpStatus.OK, controller.deleteTask(ID).getStatusCode());
    }

    @Test(expected = FeignException.class)
    public void should_throw_feign_exception_when_controller_returns_http_status_NOTFOUND_or_BADREQUEST_during_delete_task() {
        when(controller.deleteTask(ID)).thenThrow(exception);
        controller.deleteTask(ID);
    }


}
