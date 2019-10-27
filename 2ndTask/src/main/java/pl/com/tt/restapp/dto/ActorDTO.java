package pl.com.tt.restapp.dto;

import lombok.Data;

@Data
public class ActorDTO {

    private Long actorId;

    private String firstName;

    private String lastName;

    private Integer age;
}
