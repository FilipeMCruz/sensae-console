package sharespot.services.lgt92gpsdatagateway.infrastructure.endpoint.rest.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("sharespot.services.lgt92gpsdatagateway.infrastructure.endpoint.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiDetails())
                .pathMapping("/")
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "lgt92 gps Sensor Gateway",
                "Backend Microservice responsible for collecting sensor data - Entry Point for the lgt92 gps Sensor Data",
                "0.2",
                "Terms of service remain undefined",
                new Contact("Sharespot", "https://sharespot.pt", "geral@sharespot.pt"),
                "All Rights Reserved",
                "",
                Collections.emptyList());
    }
}
