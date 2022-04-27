package pt.sensae.services.device.commander.backend.infrastructure.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpDeadLetterConfiguration {

    public static final String DEAD_LETTER_EXCHANGE = "dlx";
    public static final String DEAD_LETTER_QUEUE = "dlq";

    @Bean
    Exchange deadLetterExchange() {
        return ExchangeBuilder.directExchange(DEAD_LETTER_EXCHANGE).build();
    }

    @Bean
    Queue deadQueue() {
        return QueueBuilder
                .durable(DEAD_LETTER_QUEUE)
                .build();
    }

    @Bean
    Binding deadLetterBind() {
        return BindingBuilder.bind(deadQueue()).to(deadLetterExchange()).with(DEAD_LETTER_QUEUE).noargs();
    }
}
