package batiskav.blps_lab1.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class SpringWebConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }




}
