package pl.com.tt.restapp.utils;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Component;
import pl.com.tt.restapp.domain.Actor;
import pl.com.tt.restapp.domain.Movie;
import pl.com.tt.restapp.dto.ActorDTO;
import pl.com.tt.restapp.dto.MovieDTO;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class Utils {

    public Object mapperFromDtoToEntity(Object o) throws InvocationTargetException, IllegalAccessException {
        if (o instanceof MovieDTO) {
            Movie movie = new Movie();
            BeanUtilsBean.getInstance().copyProperties(movie, o);
            return movie;
        } else if (o.getClass() == ActorDTO.class) {
            Actor actor = new Actor();
            BeanUtilsBean.getInstance().copyProperties(actor, o);
            return actor;
        } else {
            throw new IllegalAccessException();
        }
    }

    public Object mapperFromEntityToDto(Object o) throws InvocationTargetException, IllegalAccessException {
        if (o instanceof Movie) {
            MovieDTO movieDTO = new MovieDTO();
            BeanUtilsBean.getInstance().copyProperties(movieDTO, o);
            return movieDTO;
        } else if (o.getClass() == Actor.class) {
            ActorDTO actorDTO = new ActorDTO();
            BeanUtilsBean.getInstance().copyProperties(actorDTO, o);
            return actorDTO;
        } else {
            throw new IllegalAccessException();

        }
    }

    public void writeToCSV(List<Actor> actors) throws IOException {
        CsvMapper mapper = new CsvMapper();
        mapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        CsvSchema schema = mapper.schemaFor(Actor.class);
        schema = schema.withColumnSeparator(',').withUseHeader(true).withLineSeparator("\n");

        // output writer
        ObjectWriter myObjectWriter = mapper.writer(schema);
        File tempFile = new File("actors.csv");
        FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
        OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);
        myObjectWriter.writeValue(writerOutputStream, actors);
    }




}
