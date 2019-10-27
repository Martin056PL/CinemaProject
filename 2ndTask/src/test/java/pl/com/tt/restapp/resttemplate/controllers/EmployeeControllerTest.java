package pl.com.tt.restapp.resttemplate.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.com.tt.restapp.resttemplate.domainresttemplate.EmployeeRestTemplate;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @Mock
    RestTemplate template;

    @Mock
    ResponseEntity<List<EmployeeRestTemplate>> response;

    @InjectMocks
    EmployeeController employeeController;

    private static final String url = "http://localhost:12345/employees";
    private static final HttpMethod method = HttpMethod.GET;
    private static final HttpEntity<?> entity = null;
    private static final ParameterizedTypeReference<List<EmployeeRestTemplate>> reference = new ParameterizedTypeReference<>(){};

    @Test
    public void should_return_status_ok_when_controller_returns_empty_list(){
        when(template.exchange(url,method,entity,reference)).thenReturn(response);
        when(response.getBody()).thenReturn(Collections.emptyList());
        assertEquals(HttpStatus.OK, employeeController.getAllEmployees().getStatusCode());
    }

}
