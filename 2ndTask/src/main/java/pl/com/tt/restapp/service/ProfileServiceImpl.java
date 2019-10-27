package pl.com.tt.restapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final Environment environment;

    @Override
    public String returnCurrentProfile() {
        return Arrays.toString(environment.getActiveProfiles());
    }
}
