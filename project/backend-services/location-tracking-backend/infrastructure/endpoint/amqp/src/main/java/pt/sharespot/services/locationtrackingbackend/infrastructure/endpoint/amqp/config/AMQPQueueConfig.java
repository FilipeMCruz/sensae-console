package pt.sharespot.services.locationtrackingbackend.infrastructure.endpoint.amqp.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPQueueConfig {

    @Bean
    public Queue GPSDataQueue() {
        return new Queue("GPSDataQueue", false);
    }
}
