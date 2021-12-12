package sharespot.services.fastdatastore.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "sharespot.services")
@EnableJdbcRepositories(basePackages = "sharespot.services")
@EntityScan(basePackages = "sharespot.services.infrastructure.persistence.questdb")
public class DataStoreApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DataStoreApplication.class, args);
    }

}
