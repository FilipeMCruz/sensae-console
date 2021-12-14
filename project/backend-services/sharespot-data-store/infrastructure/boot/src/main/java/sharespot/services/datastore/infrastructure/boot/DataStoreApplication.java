package sharespot.services.datastore.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "sharespot.services")
public class DataStoreApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DataStoreApplication.class, args);
    }

}
