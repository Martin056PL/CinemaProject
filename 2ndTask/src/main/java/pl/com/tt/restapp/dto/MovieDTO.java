package pl.com.tt.restapp.dto;

import lombok.Data;
import pl.com.tt.restapp.domain.Actor;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieDTO {

    private Long movieId;

    private String title;

    private LocalDate datePremiere;

    private String type;

    private List<Actor> actors;
}
