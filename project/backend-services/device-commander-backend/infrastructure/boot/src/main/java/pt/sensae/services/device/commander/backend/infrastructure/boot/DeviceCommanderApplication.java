package pt.sensae.services.device.commander.backend.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"pt.sensae.services"})
@EnableJpaRepositories("pt.sensae.services")
@EntityScan("pt.sensae.services")
public class DeviceCommanderApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DeviceCommanderApplication.class, args);
    }

}
