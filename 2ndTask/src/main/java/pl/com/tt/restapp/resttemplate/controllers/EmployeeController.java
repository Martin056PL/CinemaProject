package pl.com.tt.restapp.resttemplate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.com.tt.restapp.resttemplate.domainresttemplate.EmployeeRestTemplate;

import java.util.List;

@CrossOrigin (origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmployeeController {

    private final RestTemplate template;

    private static final String endpoint = "http://localhost:12345/employees";

    @GetMapping(value = "employees")
    public ResponseEntity<List<EmployeeRestTemplate>> getAllEmployees() {
        template.getInterceptors().add(new BasicAuthorizationInterceptor("dziewulskij", "dj123"));
        ResponseEntity<List<EmployeeRestTemplate>> response = template.exchange(
                endpoint,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        List<EmployeeRestTemplate> employees = response.getBody();
        return ResponseEntity.ok(employees);

    }
}
