package pt.sensae.services.fleet.management.backend.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"pt.sensae.services", "pt.sharespot"})
@EnableJdbcRepositories(basePackages = "pt.sensae.services")
public class FleetManagementBackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(FleetManagementBackendApplication.class, args);
    }
}