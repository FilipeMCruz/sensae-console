package pt.sensae.services.smart.irrigation.backend.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@ComponentScan(basePackages = {"pt.sensae.services", "pt.sharespot"})
public class SmartIrrigationBackendApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SmartIrrigationBackendApplication.class, args);
    }
}
