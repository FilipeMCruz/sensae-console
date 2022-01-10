package sharespot.services.locationtrackingbackend.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"sharespot.services", "pt.sharespot"})
@EnableJdbcRepositories(basePackages = "sharespot.services")
public class LocationTrackingBackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LocationTrackingBackendApplication.class, args);
    }
}
