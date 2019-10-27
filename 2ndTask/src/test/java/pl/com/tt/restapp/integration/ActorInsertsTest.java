package pl.com.tt.restapp.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.repository.ActorRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActorInsertsTest {

    @Autowired
    private ActorRepository repository;
    private List<Actor> actorList;

    @Before
    public void init(){
        actorList = repository.findAll();
    }

    @Test
    public void should_size_of_data_base_be_equal_to_size_of_csv(){
        assertEquals(100, actorList.size());
    }
}
