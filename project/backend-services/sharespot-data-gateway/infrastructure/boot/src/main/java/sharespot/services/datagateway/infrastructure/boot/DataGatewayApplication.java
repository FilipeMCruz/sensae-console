package sharespot.services.datagateway.infrastructure.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"sharespot.services", "pt.sharespot"})
public class DataGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataGatewayApplication.class, args);
    }
}
