package pt.sensae.services.rule.management.backend.infrastructure.boot.config;

import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfiguration {

    @Bean
    public KieServices kieServices() {
        var services = KieServices.Factory.get();
        KieBaseConfiguration config = services.newKieBaseConfiguration();

        config.setOption(EventProcessingOption.STREAM);
        return services;
    }
}
