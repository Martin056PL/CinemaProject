package pl.com.tt.restapp.exceptionservice.handlers;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.com.tt.restapp.exceptionservice.errorapi.ApiError;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j(topic = "application.logger")
public class AdviceHandler extends ResponseEntityExceptionHandler {

    private final ApiError apiError;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> notFoundHandler(Exception e, HttpServletRequest request) {
        log.error("APPLICATION THROWS EXCEPTION: " + e.getClass() + ",\n CAUSE OF EXCEPTION: "
                + e.getCause().toString() + ",\n EXCEPTION MESSAGE: " + e.getMessage()
                + ",\n EXCEPTION STACK TRACE: " + Arrays.toString(e.getStackTrace())
                + "\n REQUEST ADDRESS: " + request.getRequestURL());
        apiError.setMessage("Ups.... Something goes wrong...");
        apiError.setStatus(HttpStatus.I_AM_A_TEAPOT);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(FeignException.class)
    ResponseEntity<Object> handleEntityBadRequest(FeignException ex) {
        int integerStatus = ex.status();
        HttpStatus status = HttpStatus.resolve(integerStatus);

        if (status == null) {
            log.error("Throws internal server error.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        apiError.setMessage(ex.getMessage());
        apiError.setStatus(status);
        log.debug("Feign Client throws this exception: {}", apiError);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
