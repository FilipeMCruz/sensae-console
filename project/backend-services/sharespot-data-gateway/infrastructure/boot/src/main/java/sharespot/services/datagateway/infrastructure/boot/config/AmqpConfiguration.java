package sharespot.services.datagateway.infrastructure.boot.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.sharespot.iot.core.routing.exchanges.IoTCoreExchanges;

@Configuration
public class AmqpConfiguration {

    @Bean
    public TopicExchange topic() {
        return new TopicExchange(IoTCoreExchanges.DATA_EXCHANGE);
    }

}
