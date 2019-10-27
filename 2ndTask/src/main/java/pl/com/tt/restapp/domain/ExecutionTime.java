package pl.com.tt.restapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "execution_time")
public class ExecutionTime {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "execution_time_id")
    private Long executionTimeId;

    @Column(name = "method")
    private String method;

    @Column(name = "time_elapsed")
    private Long timeElapsed;

}
