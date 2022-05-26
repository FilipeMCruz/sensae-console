package pt.sensae.services.alert.dispatcher.backend.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"pt.sensae.services", "pt.sharespot"})
public class AlertDispatcherApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AlertDispatcherApplication.class, args);
    }

}
