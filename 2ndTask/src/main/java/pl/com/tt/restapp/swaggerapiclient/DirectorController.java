package pl.com.tt.restapp.swaggerapiclient;


import io.swagger.client.ApiClient;
import io.swagger.client.api.DirectorControllerApi;
import io.swagger.client.model.DirectorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.client.ApiException;

import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/directors")
@Slf4j(topic = "application.logger")
public class DirectorController {

    DirectorControllerApi dirApi = new DirectorControllerApi(initClient());
    private ApiClient initClient(){
        ApiClient client = new ApiClient();
        client.setBasePath("http://localhost:8081/api");
        return client;
    }


    @GetMapping
    public ResponseEntity getAllDirectors() throws ApiException {
        log.debug("Endpoint address: 'api/directors' with GET method");
        List<DirectorDTO> listOfDirectors = dirApi.getAllDirectorsAsListUsingGET();
        log.debug("Application returned list of all movies from DB with id: {}", Arrays.toString(listOfDirectors.stream().map(DirectorDTO::getId).toArray()));
        return ResponseEntity.ok().body(listOfDirectors);

    }

    @GetMapping("{id}")
    public ResponseEntity<DirectorDTO> getDirectorById(@PathVariable Long id) throws ApiException {
        log.debug("Endpoint address: 'api/directors/{id}' with GET method\nrequests params: id = {}", id);
        DirectorDTO returnedDirector = dirApi.getDirectorByIdUsingGET(id);
        return ResponseEntity.ok(returnedDirector);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody DirectorDTO dto) throws ApiException {
        log.debug("Endpoint address: 'api/directors' with POST method\nrequest body: {}", dto);
        return ResponseEntity.ok(dirApi.addDirectorUsingPOST(dto));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody DirectorDTO dto) throws ApiException {
        log.debug("Endpoint address: 'api/directors/id' with PUT method\nrequests params: id = {}", id);
        log.debug("Request body: {}", dto);
        return ResponseEntity.ok(dirApi.updateDirectorUsingPUT(dto, id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) throws ApiException {
        log.debug("Endpoint address: 'api/directors/id' with DELETE method\nrequests params: id = {}", id);
        return ResponseEntity.ok(dirApi.deleteDirectorByIdUsingDELETE(id));
    }
}
