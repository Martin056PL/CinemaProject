package pl.com.tt.restapp.aspect;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
class Measurement {

    private Object joinPoint;
    private Long time;

}
