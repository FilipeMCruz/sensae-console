package sharespot.services.datagateway.infrastructure.boot.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.IoTCoreTopic;

@Configuration
public class AmqpConfiguration {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(IoTCoreTopic.DATA_EXCHANGE);
    }

}
