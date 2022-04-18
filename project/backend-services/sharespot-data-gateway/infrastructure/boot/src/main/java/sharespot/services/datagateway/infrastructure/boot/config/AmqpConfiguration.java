package sharespot.services.datagateway.infrastructure.boot.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sharespot.services.datagateway.infrastructure.endpoint.amqp.SensorDataSupplier;

@Configuration
public class AmqpConfiguration {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(SensorDataSupplier.TOPIC_EXCHANGE);
    }

}
