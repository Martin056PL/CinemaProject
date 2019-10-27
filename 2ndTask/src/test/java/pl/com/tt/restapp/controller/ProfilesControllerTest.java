package pl.com.tt.restapp.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.com.tt.restapp.service.ProfileServiceImpl;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfilesControllerTest {

    @Mock
    ProfileServiceImpl service;

    @InjectMocks
    ProfilesController controller;

    @Test
    public void should_return_active_profile_which_is_develop(){
        when(service.returnCurrentProfile()).thenReturn("[Develop]");
        Assert.assertEquals("[Develop]", controller.returnCurrentProfile().getBody());
    }
}
