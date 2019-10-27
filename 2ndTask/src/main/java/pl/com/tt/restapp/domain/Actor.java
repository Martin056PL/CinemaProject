package pl.com.tt.restapp.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.FetchMode.JOIN;

@Data
@NoArgsConstructor
@Entity
@Table(name = "actors")
public class Actor implements Serializable {

    private static final long serialVersionUID = 6460140826650392604L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long actorId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Integer age;

    @ManyToMany(mappedBy = "actors")
    @ToString.Exclude
    @Fetch(JOIN)
    private List<Movie> movie = new ArrayList<>();

    @JsonIgnore
    public List<Movie> getMovie() {
        return this.movie;
    }
}
