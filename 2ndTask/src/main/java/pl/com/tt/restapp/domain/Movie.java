package pl.com.tt.restapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.FetchMode.JOIN;

@Data
@Entity
@NoArgsConstructor
@Table(name = "movies")
public class Movie implements Serializable {

    private static final long serialVersionUID = 3683778473783051508L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long movieId;

    private String title;

    @Column(name = "date_premiere")
    private LocalDate datePremiere;

    @Column(name = "genre")
    private String type;


    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "movies_actors"
            , joinColumns = {@JoinColumn(name = "movie_id")}
            , inverseJoinColumns = {@JoinColumn(name = "actor_id")})
    @Fetch(JOIN)
    private List<Actor> actors = new ArrayList<>();

    @JsonIgnore
    public List<Actor> getActors() {
        return this.actors;
    }
}
