package pt.sharespot.services.locationtrackingbackend.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "pt.sharespot.services")
public class LocationTrackingBackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(LocationTrackingBackendApplication.class, args);
    }
}
