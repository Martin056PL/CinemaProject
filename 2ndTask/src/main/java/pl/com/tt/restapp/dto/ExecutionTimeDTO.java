package pl.com.tt.restapp.dto;

import lombok.Data;

@Data
public class ExecutionTimeDTO {

    private String method;

    private Long timeElapsed;
}
