package pt.sensae.services.data.validator.backend.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"pt.sensae.services", "pt.sharespot"})
public class DataValidatorApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DataValidatorApplication.class, args);
    }

}
