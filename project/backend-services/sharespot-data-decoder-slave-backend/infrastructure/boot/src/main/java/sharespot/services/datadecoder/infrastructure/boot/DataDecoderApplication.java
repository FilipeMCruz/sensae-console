package sharespot.services.datadecoder.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"sharespot.services", "pt.sharespot"})
@EnableJpaRepositories("sharespot.services")
@EntityScan("sharespot.services")
public class DataDecoderApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DataDecoderApplication.class, args);
    }

}
