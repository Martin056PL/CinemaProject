package pl.com.tt.restapp.configutarion;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import pl.com.tt.restapp.soap.MovieEndpoint;
import pl.com.tt.restapp.soap.sources.MoviesPort;

import javax.xml.ws.Endpoint;

@EnableWs
@Configuration
public class SoapConfig extends WsConfigurerAdapter {

    @Bean
    public XsdSchema positionsSchema() {
        return new SimpleXsdSchema(new ClassPathResource("movie.xsd"));
    }

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        return new ServletRegistrationBean<>(new CXFServlet(), "/ws/*");
    }


    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), movieEndpoint());
        endpoint.getProperties().put("schema-validation-enabled", "true");
        endpoint.publish("/moviesWS");
        return endpoint;
    }

    @Bean
    public MoviesPort movieEndpoint() {
        return new MovieEndpoint();
    }
}


