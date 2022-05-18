package sharespot.services.data.decoder.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"sharespot.services", "pt.sharespot"})
public class DataDecoderApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DataDecoderApplication.class, args);
    }

}
