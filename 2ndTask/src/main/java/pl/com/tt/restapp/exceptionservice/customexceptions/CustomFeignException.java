package pl.com.tt.restapp.exceptionservice.customexceptions;

import feign.FeignException;

public class CustomFeignException extends FeignException {
    public CustomFeignException(int status, String message) {
        super(status, message);
    }
}
