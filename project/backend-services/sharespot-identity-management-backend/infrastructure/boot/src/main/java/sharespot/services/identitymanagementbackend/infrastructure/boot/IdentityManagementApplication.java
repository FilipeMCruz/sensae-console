package sharespot.services.identitymanagementbackend.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "sharespot.services")
@EnableJpaRepositories("sharespot.services")
@EntityScan("sharespot.services")
public class IdentityManagementApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(IdentityManagementApplication.class, args);
    }

}
