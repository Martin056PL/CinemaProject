package pl.com.tt.restapp.specification;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import pl.com.tt.restapp.domain.Movie;

@And({
        @Spec(path = "title", spec = Like.class),
        @Spec(path = "datePremiere", spec = Equal.class),
        @Spec(path = "type", spec = Like.class)
})
public interface MovieSpecification extends Specification<Movie> {
}
