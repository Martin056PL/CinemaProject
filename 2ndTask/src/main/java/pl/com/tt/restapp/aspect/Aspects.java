package pl.com.tt.restapp.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import pl.com.tt.restapp.domain.ExecutionTime;
import pl.com.tt.restapp.service.ExecutionTimeService;

@Aspect
@Component
@RequiredArgsConstructor
public class Aspects {

    private final ExecutionTimeService service;

    @Around("execution(* pl.com.tt.restapp.controller.MovieRestController.*(..))")
    public Object TimerStart(ProceedingJoinPoint joinPoint) throws Throwable {
        return useInterceptor(joinPoint);
    }

    private Object useInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        String signature = joinPoint.getSignature().toShortString();

        Measurement measurement = measure(joinPoint);

        ExecutionTime executionTime = ExecutionTime.builder()
                .method(signature)
                .timeElapsed(measurement.getTime())
                .build();

        service.saveExecutionTimeToDataBase(executionTime);
        return measurement.getJoinPoint();
    }


    public Measurement measure(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch watch = new StopWatch();
        watch.start();
        Object o = joinPoint.proceed();
        watch.stop();

        return Measurement.builder()
                .joinPoint(o)
                .time(watch.getLastTaskTimeMillis())
                .build();
    }
}
